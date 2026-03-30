package com.fredodev.riee.dentist.infrastructure.config;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecialityDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SpecialityDataInitializer.class);

    private final SpecialityRepository specialityRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<SpecialityEntity> defaults = List.of(
                SpecialityEntity.builder().nombre("Odontología General").descripcion("Atención básica de la salud bucal, incluyendo limpieza, diagnóstico y tratamientos preventivos.").build(),
                SpecialityEntity.builder().nombre("Ortodoncia").descripcion("Especialidad que corrige la posición de los dientes y mandíbulas mediante brackets y alineadores.").build(),
                SpecialityEntity.builder().nombre("Endodoncia").descripcion("Tratamiento de la pulpa dental, como las endodoncias o tratamientos de conducto.").build(),
                SpecialityEntity.builder().nombre("Periodoncia").descripcion("Prevención, diagnóstico y tratamiento de enfermedades de las encías y tejidos de soporte del diente.").build(),
                SpecialityEntity.builder().nombre("Odontopediatría").descripcion("Atención dental especializada en niños, desde la infancia hasta la adolescencia.").build(),
                SpecialityEntity.builder().nombre("Cirugía Oral y Maxilofacial").descripcion("Procedimientos quirúrgicos en la boca, mandíbula y rostro, como extracciones complejas.").build(),
                SpecialityEntity.builder().nombre("Prostodoncia").descripcion("Rehabilitación oral mediante prótesis dentales como coronas, puentes y dentaduras.").build(),
                SpecialityEntity.builder().nombre("Implantología").descripcion("Colocación de implantes dentales para reemplazar dientes perdidos.").build(),
                SpecialityEntity.builder().nombre("Odontología Estética").descripcion("Mejora de la apariencia de los dientes mediante blanqueamientos, carillas, etc.").build(),
                SpecialityEntity.builder().nombre("Radiología Oral y Maxilofacial").descripcion("Uso de imágenes diagnósticas como radiografías para evaluar estructuras dentales.").build(),
                SpecialityEntity.builder().nombre("Patología Oral").descripcion("Estudio y diagnóstico de enfermedades que afectan la cavidad oral.").build(),
                SpecialityEntity.builder().nombre("Odontología Forense").descripcion("Aplicación de la odontología en la identificación de personas y análisis legal.").build(),
                SpecialityEntity.builder().nombre("Salud Pública Odontológica").descripcion("Enfoque en la prevención y promoción de la salud bucal a nivel comunitario.").build(),
                SpecialityEntity.builder().nombre("Rehabilitación Oral").descripcion("Restauración integral de la función y estética dental.").build(),
                SpecialityEntity.builder().nombre("Oclusión y ATM").descripcion("Estudio y tratamiento de problemas en la mordida y la articulación temporomandibular.").build()
        );

        for (SpecialityEntity speciality : defaults) {
            var existing = specialityRepository.findByNombre(speciality.getNombre());
            if (existing.isPresent()) {
                var e = existing.get();
                log.info("Speciality already exists: name='{}', id={}", e.getNombre(), e.getId());
            } else {
                SpecialityEntity saved = specialityRepository.save(speciality);
                log.info("Speciality created: name='{}', id={}", saved.getNombre(), saved.getId());
            }
        }
    }
}
