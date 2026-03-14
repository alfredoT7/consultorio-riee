package com.fredodev.riee.cloudinary.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.config.CloudinaryProperties;
import com.fredodev.riee.cloudinary.domain.exception.CloudinaryOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private static final String CLOUDINARY_API_BASE = "https://api.cloudinary.com/v1_1";

    private final CloudinaryProperties cloudinaryProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public CloudinaryUploadResponse uploadImage(MultipartFile file, String folder) {
        return uploadImage(file, folder, null);
    }

    public CloudinaryUploadResponse uploadImage(MultipartFile file, String folder, String publicId) {
        validateImage(file);

        long timestamp = Instant.now().getEpochSecond();
        String resolvedFolder = resolveFolder(folder);
        String resolvedPublicId = resolvePublicId(publicId);
        Map<String, String> signableParams = new LinkedHashMap<>();
        signableParams.put("timestamp", String.valueOf(timestamp));
        if (StringUtils.hasText(resolvedFolder)) {
            signableParams.put("folder", resolvedFolder);
        }
        if (StringUtils.hasText(resolvedPublicId)) {
            signableParams.put("public_id", resolvedPublicId);
        }

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", toFileResource(file));
        body.add("api_key", cloudinaryProperties.getApiKey());
        body.add("timestamp", String.valueOf(timestamp));
        body.add("signature", generateSignature(signableParams));
        if (StringUtils.hasText(resolvedFolder)) {
            body.add("folder", resolvedFolder);
        }
        if (StringUtils.hasText(resolvedPublicId)) {
            body.add("public_id", resolvedPublicId);
        }

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    buildUploadUri(),
                    new HttpEntity<>(body, multipartHeaders()),
                    Map.class
            );
            return mapUploadResponse(response.getBody(), file.getOriginalFilename());
        } catch (RestClientException exception) {
            throw new CloudinaryOperationException("No se pudo subir la imagen a Cloudinary", exception);
        }
    }

    public CloudinaryUploadResponse replaceImage(MultipartFile file, String folder, String previousPublicId) {
        CloudinaryUploadResponse uploadedImage = uploadImage(file, folder);

        if (!StringUtils.hasText(previousPublicId)) {
            return uploadedImage;
        }

        try {
            deleteImage(previousPublicId);
            return uploadedImage;
        } catch (RuntimeException exception) {
            tryDeleteQuietly(uploadedImage.getPublicId());
            throw new CloudinaryOperationException("Se subio la nueva imagen, pero no se pudo reemplazar la anterior", exception);
        }
    }

    public void deleteImage(String publicId) {
        if (!StringUtils.hasText(publicId)) {
            throw new CloudinaryOperationException("El publicId es obligatorio para eliminar una imagen");
        }

        long timestamp = Instant.now().getEpochSecond();
        Map<String, String> signableParams = new LinkedHashMap<>();
        signableParams.put("public_id", publicId);
        signableParams.put("timestamp", String.valueOf(timestamp));

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("public_id", publicId);
        body.add("timestamp", String.valueOf(timestamp));
        body.add("api_key", cloudinaryProperties.getApiKey());
        body.add("signature", generateSignature(signableParams));

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    buildDestroyUri(),
                    new HttpEntity<>(body, formHeaders()),
                    Map.class
            );

            String result = valueAsString(response.getBody().get("result"));
            if (!"ok".equalsIgnoreCase(result) && !"not found".equalsIgnoreCase(result)) {
                throw new CloudinaryOperationException("Cloudinary no confirmo la eliminacion de la imagen");
            }
        } catch (RestClientException exception) {
            throw new CloudinaryOperationException("No se pudo eliminar la imagen de Cloudinary", exception);
        }
    }

    public Optional<String> extractPublicId(String imageUrl) {
        if (!StringUtils.hasText(imageUrl) || !imageUrl.contains("/upload/")) {
            return Optional.empty();
        }

        try {
            String path = URI.create(imageUrl).getPath();
            String[] uploadSplit = path.split("/upload/");
            if (uploadSplit.length != 2) {
                return Optional.empty();
            }

            String publicPath = uploadSplit[1];
            if (publicPath.matches("^v\\d+/.+")) {
                publicPath = publicPath.replaceFirst("^v\\d+/", "");
            }

            int extensionIndex = publicPath.lastIndexOf('.');
            if (extensionIndex > 0) {
                publicPath = publicPath.substring(0, extensionIndex);
            }

            return StringUtils.hasText(publicPath) ? Optional.of(publicPath) : Optional.empty();
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    private CloudinaryUploadResponse mapUploadResponse(Map<?, ?> responseBody, String originalFilename) {
        if (responseBody == null || !responseBody.containsKey("secure_url") || !responseBody.containsKey("public_id")) {
            throw new CloudinaryOperationException("Cloudinary devolvio una respuesta invalida");
        }

        Map<String, Object> response = objectMapper.convertValue(responseBody, new TypeReference<>() {
        });

        return CloudinaryUploadResponse.builder()
                .url(valueAsString(response.get("secure_url")))
                .publicId(valueAsString(response.get("public_id")))
                .originalFilename(StringUtils.hasText(originalFilename) ? originalFilename : valueAsString(response.get("original_filename")))
                .format(valueAsString(response.get("format")))
                .bytes(valueAsLong(response.get("bytes")))
                .width(valueAsInteger(response.get("width")))
                .height(valueAsInteger(response.get("height")))
                .build();
    }

    private HttpHeaders multipartHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private HttpHeaders formHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private ByteArrayResource toFileResource(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String filename = StringUtils.hasText(file.getOriginalFilename()) ? file.getOriginalFilename() : "image";

            return new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };
        } catch (IOException exception) {
            throw new CloudinaryOperationException("No se pudo leer el archivo recibido", exception);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CloudinaryOperationException("Debes enviar una imagen");
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !contentType.startsWith("image/")) {
            throw new CloudinaryOperationException("El archivo debe ser una imagen valida");
        }
    }

    private String resolveFolder(String folder) {
        return StringUtils.hasText(folder) ? folder.trim() : cloudinaryProperties.getDefaultFolder();
    }

    private String resolvePublicId(String publicId) {
        return StringUtils.hasText(publicId) ? publicId.trim() : null;
    }

    private String buildUploadUri() {
        return CLOUDINARY_API_BASE + "/" + cloudinaryProperties.getCloudName() + "/image/upload";
    }

    private String buildDestroyUri() {
        return CLOUDINARY_API_BASE + "/" + cloudinaryProperties.getCloudName() + "/image/destroy";
    }

    private String generateSignature(Map<String, String> params) {
        String stringToSign = params.entrySet().stream()
                .filter(entry -> StringUtils.hasText(entry.getValue()))
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        String payload = stringToSign + cloudinaryProperties.getApiSecret();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            return IntStream.range(0, hash.length)
                    .mapToObj(index -> String.format("%02x", hash[index] & 0xff))
                    .collect(Collectors.joining());
        } catch (NoSuchAlgorithmException exception) {
            throw new CloudinaryOperationException("No se pudo generar la firma para Cloudinary", exception);
        }
    }

    private void tryDeleteQuietly(String publicId) {
        try {
            deleteImage(publicId);
        } catch (RuntimeException ignored) {
        }
    }

    private String valueAsString(Object value) {
        return value != null ? String.valueOf(value) : null;
    }

    private Long valueAsLong(Object value) {
        return value instanceof Number number ? number.longValue() : null;
    }

    private Integer valueAsInteger(Object value) {
        return value instanceof Number number ? number.intValue() : null;
    }
}
