package com.fredodev.riee.treatment.infrastructure.config;

import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentRepository;
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
public class TreatmentDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TreatmentDataInitializer.class);

    private static final List<TreatmentEntity> DEFAULT_TREATMENTS = List.of(

        // ── DIAGNOSTICO Y PREVENCION ──────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Evaluacion oral integral")
            .descripcion("Evaluacion clinica general de la cavidad oral para detectar caries, enfermedad periodontal y otras patologias.")
            .procedimiento("Se realiza anamnesis, inspeccion clinica, odontograma y plan de tratamiento inicial.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(150)
            .notasAdicionales("Incluye revision inicial y recomendaciones generales.")
            .imagen_referencial("https://example.com/images/evaluacion-oral.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Radiografia panoramica")
            .descripcion("Imagen radiografica que muestra toda la denticion, huesos maxilares y estructuras adyacentes.")
            .procedimiento("Se posiciona al paciente en el equipo panoramico y se toma la proyeccion en una sola exposicion.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(80)
            .notasAdicionales("Indispensable para planificacion de tratamientos complejos.")
            .imagen_referencial("https://example.com/images/panoramica.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Radiografia periapical")
            .descripcion("Imagen radiografica de una pieza dental y su tejido periapical para diagnostico localizado.")
            .procedimiento("Se coloca el sensor intraoral y se toma la exposicion dirigida a la pieza de interes.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(30)
            .notasAdicionales("Se usa para diagnostico de caries, lesiones periapicales y control endodontico.")
            .imagen_referencial("https://example.com/images/periapical.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Radiografia de aleta de mordida")
            .descripcion("Proyeccion radiografica interproximal para detectar caries interdentales y evaluar cresta osea.")
            .procedimiento("El paciente muerde una aleta de posicionamiento mientras se toma la imagen de las zonas posteriores.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(35)
            .notasAdicionales("Tecnica de eleccion para deteccion temprana de caries interproximales.")
            .imagen_referencial("https://example.com/images/aleta-mordida.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Tomografia cone beam dental (CBCT)")
            .descripcion("Estudio tomografico tridimensional de alta precision para planificacion de implantes y cirugia.")
            .procedimiento("Se realiza la captura volumetrica con el equipo CBCT y se analiza en software especializado.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(250)
            .notasAdicionales("Requerido para implantes, cirugia de terceros molares complejos y planificacion ortodoncia.")
            .imagen_referencial("https://example.com/images/cbct.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Limpieza dental")
            .descripcion("Remocion de placa bacteriana, sarro y manchas superficiales para mejorar la salud gingival.")
            .procedimiento("Se efectua profilaxis con ultrasonido, pulido dental y aplicacion de recomendaciones de higiene.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(120)
            .notasAdicionales("Recomendado cada 6 meses.")
            .imagen_referencial("https://example.com/images/limpieza-dental.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Aplicacion de fluor profesional")
            .descripcion("Aplicacion topica de fluor de alta concentracion para reforzar el esmalte y prevenir caries.")
            .procedimiento("Se aislala zona, se aplica gel o barniz fluorado por el tiempo indicado segun el protocolo.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(50)
            .notasAdicionales("Especialmente indicado en ninos y pacientes con alto riesgo cariogenico.")
            .imagen_referencial("https://example.com/images/fluor.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Sellantes de fosas y fisuras")
            .descripcion("Aplicacion de resina selladora en las superficies oclusales de molares para prevenir caries.")
            .procedimiento("Se limpian y acondicionan las fisuras, se aplica el sellante y se fotopolimeriza.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(60)
            .notasAdicionales("Preventivo. Mayor beneficio en primeros y segundos molares permanentes de ninos.")
            .imagen_referencial("https://example.com/images/sellantes.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Atencion dental de urgencia")
            .descripcion("Manejo inmediato de dolor agudo, traumatismos, infecciones o fracturas dentales.")
            .procedimiento("Se evalua la urgencia, se controla el dolor con anestesia local y se realiza el tratamiento paliativo.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(100)
            .notasAdicionales("No incluye el tratamiento definitivo que se programa en cita posterior.")
            .imagen_referencial("https://example.com/images/urgencia.jpg")
            .build(),

        // ── RESTAURADORA ──────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Obturacion con resina")
            .descripcion("Tratamiento restaurador para dientes afectados por caries de pequena o mediana extension.")
            .procedimiento("Se elimina el tejido afectado, se desinfecta la cavidad y se reconstruye con resina compuesta.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(180)
            .notasAdicionales("El costo puede variar segun el numero de superficies.")
            .imagen_referencial("https://example.com/images/resina-dental.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Obturacion con ionomero de vidrio")
            .descripcion("Restauracion con material adhesivo que libera fluor, indicado en zonas cervicales y pacientes con alto riesgo de caries.")
            .procedimiento("Se limpia la cavidad, se acondiciona con acido poliacrilico y se aplica el ionomero.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(140)
            .notasAdicionales("Menor resistencia al desgaste que la resina. Indicado en lesiones no expuestas a alta carga oclusal.")
            .imagen_referencial("https://example.com/images/ionomero.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Incrustacion inlay de porcelana")
            .descripcion("Restauracion indirecta de porcelana fabricada en laboratorio para cavidades de mediana extension.")
            .procedimiento("Se prepara la cavidad, se toma impresion, se coloca provisional y se cementa el inlay definitivo.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(600)
            .notasAdicionales("Mayor durabilidad y estetica que las restauraciones directas. Requiere dos sesiones.")
            .imagen_referencial("https://example.com/images/inlay.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Incrustacion onlay de porcelana")
            .descripcion("Restauracion indirecta que cubre una o mas cuspides del diente cuando la destruccion es extensa.")
            .procedimiento("Se prepara la pieza incluyendo las cuspides comprometidas, se toma impresion y se cementa el onlay.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(750)
            .notasAdicionales("Alternativa conservadora a la corona total cuando el tejido remanente es suficiente.")
            .imagen_referencial("https://example.com/images/onlay.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Reconstruccion coronal con perno de fibra")
            .descripcion("Rehabilitacion de diente tratado endodonticamente mediante perno intrarradicular de fibra de vidrio.")
            .procedimiento("Se desobtura el conducto parcialmente, se cimenta el perno de fibra y se reconstruye la corona con resina.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(320)
            .notasAdicionales("Necesario cuando la destruccion coronaria es mayor al 50%. Previa endodoncia completada.")
            .imagen_referencial("https://example.com/images/perno-fibra.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Corona provisional")
            .descripcion("Corona temporal fabricada en resina para proteger la pieza mientras se fabrica la restauracion definitiva.")
            .procedimiento("Se talla la pieza, se toma impresion y se confecciona la corona provisional en resina acrilo.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(120)
            .notasAdicionales("Generalmente incluida en el costo de la restauracion definitiva.")
            .imagen_referencial("https://example.com/images/corona-provisional.jpg")
            .build(),

        // ── ENDODONCIA ────────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Endodoncia unirradicular")
            .descripcion("Tratamiento endodontico en dientes con un solo conducto radicular para eliminar infeccion pulpar.")
            .procedimiento("Se accede al conducto, se instrumenta, desinfecta y obtura el sistema de conductos.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(350)
            .notasAdicionales("Puede requerir radiografia de control postoperatorio.")
            .imagen_referencial("https://example.com/images/endodoncia.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Endodoncia birradicular")
            .descripcion("Tratamiento de conductos en dientes con dos raices, como premolares superiores o incisivos inferiores.")
            .procedimiento("Se localiza, instrumenta y obtura cada conducto de forma independiente bajo magnificacion.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(500)
            .notasAdicionales("Mayor complejidad anatomica que los unirradiculares.")
            .imagen_referencial("https://example.com/images/endodoncia-2r.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Endodoncia multirradicular")
            .descripcion("Tratamiento de conductos en molares con tres o mas raices y alta complejidad anatomica.")
            .procedimiento("Se localizan todos los conductos, se instrumentan con limas rotatorias y se obturan con gutapercha.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(700)
            .notasAdicionales("Puede requerir magnificacion con microscopio endodontico.")
            .imagen_referencial("https://example.com/images/endodoncia-molar.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Retratamiento endodontico")
            .descripcion("Reintervencion de un diente previamente tratado endodonticamente con fracaso o infeccion persistente.")
            .procedimiento("Se retira la obturacion existente, se reinstrumenta, desinfecta profundamente y se vuelve a obturar.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(800)
            .notasAdicionales("Mas complejo que el tratamiento inicial por la presencia de material previo.")
            .imagen_referencial("https://example.com/images/retratamiento.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Apicectomia")
            .descripcion("Cirugia periapical para eliminar lesion quistica o granulomatosa que no responde a tratamiento convencional.")
            .procedimiento("Se realiza colgajo, osteotomia, reseccion apical, curetaje de la lesion y retrobturacion.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(900)
            .notasAdicionales("Procedimiento quirurgico de ultimo recurso para conservar la pieza.")
            .imagen_referencial("https://example.com/images/apicectomia.jpg")
            .build(),

        // ── PERIODONCIA ───────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Raspado y alisado radicular")
            .descripcion("Tratamiento periodontal basico para controlar gingivitis o periodontitis inicial.")
            .procedimiento("Se realiza raspado subgingival, alisado radicular y control de inflamacion periodontal.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(280)
            .notasAdicionales("Puede dividirse por cuadrantes.")
            .imagen_referencial("https://example.com/images/periodoncia.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Cirugia periodontal con colgajo")
            .descripcion("Intervencion quirurgica para eliminar bolsas periodontales profundas que no responden a tratamiento basico.")
            .procedimiento("Se realiza incision, levantamiento de colgajo, debridamiento radicular y sutura por cuadrantes.")
            .semanasEstimadas(4)
            .costoBaseTratamiento(700)
            .notasAdicionales("Indicado en periodontitis moderada a severa con bolsas mayores a 5mm.")
            .imagen_referencial("https://example.com/images/colgajo-periodontal.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Injerto gingival libre")
            .descripcion("Trasplante de tejido palatino para aumentar la banda de encia adherida o cubrir recesiones gingivales.")
            .procedimiento("Se toma el injerto del paladar, se prepara el lecho receptor y se sutura el tejido donante.")
            .semanasEstimadas(4)
            .costoBaseTratamiento(850)
            .notasAdicionales("Requiere cuidados postoperatorios especificos en zona donante y receptora.")
            .imagen_referencial("https://example.com/images/injerto-gingival.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Alargamiento de corona clinica")
            .descripcion("Cirugia periodontal para exponer mayor estructura dental permitiendo restauraciones en margenes subgingivales.")
            .procedimiento("Se realiza gingivectomia u osteotomia segun el caso para reubicar el margen gingival.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(600)
            .notasAdicionales("Requiere cicatrizacion de 6 a 8 semanas antes de tomar impresion definitiva.")
            .imagen_referencial("https://example.com/images/alargamiento-corona.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Frenectomia")
            .descripcion("Escision quirurgica del frenillo labial o lingual que genera tension o diastema.")
            .procedimiento("Se aplica anestesia local, se secciona el frenillo con bisturi o laser y se sutura si es necesario.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(250)
            .notasAdicionales("Puede realizarse con laser sin necesidad de puntos.")
            .imagen_referencial("https://example.com/images/frenectomia.jpg")
            .build(),

        // ── CIRUGIA ORAL ──────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Exodoncia simple")
            .descripcion("Extraccion de pieza dental con tecnica simple y manejo basico postoperatorio.")
            .procedimiento("Se aplica anestesia local, se luxa la pieza y se realiza la extraccion con control de hemostasia.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(250)
            .notasAdicionales("Incluye indicaciones postextraccion.")
            .imagen_referencial("https://example.com/images/exodoncia-simple.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Exodoncia quirurgica de tercer molar")
            .descripcion("Extraccion quirurgica de muela del juicio incluida o semincluida con procedimiento especializado.")
            .procedimiento("Se realiza colgajo, osteotomia si corresponde, odontoseccion, extraccion y sutura.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(550)
            .notasAdicionales("Puede requerir medicacion complementaria. El costo varia segun la inclusion.")
            .imagen_referencial("https://example.com/images/tercer-molar.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Extirpacion de quiste odontogenico")
            .descripcion("Remocion quirurgica de quiste radicular, dentario o folicular con manejo del tejido oseo.")
            .procedimiento("Se realiza colgajo, curetaje del quiste, limpieza del lecho oseo y sutura. Se envia muestra a biopsia.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(1000)
            .notasAdicionales("Requiere estudio histopatologico para confirmacion diagnostica.")
            .imagen_referencial("https://example.com/images/quiste.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Biopsia de tejidos blandos")
            .descripcion("Toma de muestra de tejido sospechoso de la mucosa oral para estudio histopatologico.")
            .procedimiento("Se realiza anestesia local, se toma la muestra con bisturi o punch y se sutura.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(300)
            .notasAdicionales("No incluye el costo del laboratorio de patologia.")
            .imagen_referencial("https://example.com/images/biopsia.jpg")
            .build(),

        // ── IMPLANTOLOGIA ─────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Implante dental oseointegrado")
            .descripcion("Colocacion de fixture de titanio en hueso alveolar para reemplazar raiz dental perdida.")
            .procedimiento("Se realiza colgajo, osteotomia escalonada, insercion del implante y sutura. Requiere periodo de oseointegración.")
            .semanasEstimadas(16)
            .costoBaseTratamiento(1500)
            .notasAdicionales("El costo no incluye corona sobre implante. Requiere CBCT previo y suficiente volumen oseo.")
            .imagen_referencial("https://example.com/images/implante.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Regeneracion osea guiada")
            .descripcion("Tecnica quirurgica para recuperar volumen oseo perdido mediante injerto y membrana barrera.")
            .procedimiento("Se coloca el material de injerto oseo y se cubre con membrana reabsorbible o no reabsorbible.")
            .semanasEstimadas(20)
            .costoBaseTratamiento(1200)
            .notasAdicionales("Necesaria cuando hay deficiencia de hueso para colocar implante en posicion ideal.")
            .imagen_referencial("https://example.com/images/regeneracion-osea.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Corona sobre implante")
            .descripcion("Restauracion fija cementada o atornillada sobre el implante oseointegrado para devolver la funcion estetica.")
            .procedimiento("Se toma impresion del implante con transfer, se envia al laboratorio y se cementa la corona definitiva.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(1000)
            .notasAdicionales("Requiere implante previamente oseointegrado. Puede ser de porcelana, zirconia o metal-ceramica.")
            .imagen_referencial("https://example.com/images/corona-implante.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Sobredentadura sobre implantes")
            .descripcion("Protesis removible retenida por implantes para rehabilitar arcadas edentulas con mayor estabilidad.")
            .procedimiento("Se colocan 2 a 4 implantes, se oseointegran y se adapta la protesis con sistemas de retencion tipo bola o barra.")
            .semanasEstimadas(24)
            .costoBaseTratamiento(3500)
            .notasAdicionales("Alternativa economica a la protesis fija sobre implantes.")
            .imagen_referencial("https://example.com/images/sobredentadura.jpg")
            .build(),

        // ── PROTESIS ──────────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Corona dental de porcelana")
            .descripcion("Rehabilitacion mediante corona fija para restaurar anatomia, funcion y estetica dental.")
            .procedimiento("Se talla la pieza, se toma impresion, se coloca provisional y luego se cementa la corona definitiva.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(900)
            .notasAdicionales("Puede requerir provisional durante el proceso.")
            .imagen_referencial("https://example.com/images/corona-dental.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Corona de zirconia")
            .descripcion("Corona dental de alta resistencia fabricada en zirconia para mayor durabilidad y estetica natural.")
            .procedimiento("Se talla la pieza, se toma impresion digital o fisica, y se cementa la corona de zirconia.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(1100)
            .notasAdicionales("Indicada en sectores posteriores con alta carga masticatoria o en pacientes con bruxismo.")
            .imagen_referencial("https://example.com/images/zirconia.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Puente dental fijo")
            .descripcion("Restauracion fija que reemplaza uno o mas dientes perdidos apoyandose en dientes pilares adyacentes.")
            .procedimiento("Se tallan los pilares, se toma impresion, se coloca puente provisional y se cementa el definitivo.")
            .semanasEstimadas(4)
            .costoBaseTratamiento(2200)
            .notasAdicionales("El costo incluye los dos pilares y el pontico. Requiere tallado de dientes sanos.")
            .imagen_referencial("https://example.com/images/puente-fijo.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Protesis parcial removible")
            .descripcion("Aparato removible que reemplaza uno o varios dientes ausentes apoyandose en dientes remanentes.")
            .procedimiento("Se toman impresiones, se disena el esqueleto metalico, se realizan pruebas de dientes y se entrega la protesis.")
            .semanasEstimadas(4)
            .costoBaseTratamiento(800)
            .notasAdicionales("Requiere ajustes periodicos. El paciente puede retirarla para su limpieza.")
            .imagen_referencial("https://example.com/images/parcial-removible.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Protesis total (dentadura completa)")
            .descripcion("Protesis removible que reemplaza la totalidad de los dientes de una o ambas arcadas.")
            .procedimiento("Se toman impresiones anatomicas y funcionales, se registra la relacion intermaxilar y se confecciona la dentadura.")
            .semanasEstimadas(6)
            .costoBaseTratamiento(1200)
            .notasAdicionales("Puede requerir ajustes post-entrega. Se recomienda control a las 24 horas.")
            .imagen_referencial("https://example.com/images/dentadura-completa.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Reparacion de protesis")
            .descripcion("Reparacion de fractura, rebase o reposicion de diente en protesis removible existente.")
            .procedimiento("Se recibe la protesis, se evalua el dano, se realiza la reparacion en laboratorio y se entrega.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(120)
            .notasAdicionales("El costo varia segun la complejidad del dano.")
            .imagen_referencial("https://example.com/images/reparacion-protesis.jpg")
            .build(),

        // ── ESTETICA DENTAL ───────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Blanqueamiento dental")
            .descripcion("Blanqueamiento dental para mejorar el color de las piezas dentarias vitales.")
            .procedimiento("Se protege tejido gingival y se aplica agente blanqueador segun protocolo clinico.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(220)
            .notasAdicionales("Se recomienda evaluacion previa de sensibilidad.")
            .imagen_referencial("https://example.com/images/blanqueamiento.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Carilla de porcelana")
            .descripcion("Lamina ceramica ultrafina adherida a la superficie vestibular del diente para mejorar forma, color y textura.")
            .procedimiento("Se realiza minimo desgaste del esmalte, se toma impresion y se adhiere la carilla con resina de cementacion.")
            .semanasEstimadas(3)
            .costoBaseTratamiento(950)
            .notasAdicionales("Irreversible. Requiere tecnica adhesiva precisa y materiales de alta calidad estetica.")
            .imagen_referencial("https://example.com/images/carilla-porcelana.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Carilla de resina directa")
            .descripcion("Restauracion estetica directa con resina compuesta para mejorar la apariencia de dientes anteriores.")
            .procedimiento("Se acondiciona el diente, se aplica resina por capas con tecnica de estratificacion y se pule.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(300)
            .notasAdicionales("Mas economica que la de porcelana. Puede requerir retoques a largo plazo.")
            .imagen_referencial("https://example.com/images/carilla-resina.jpg")
            .build(),

        // ── ORTODONCIA ────────────────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Ortodoncia con brackets metalicos")
            .descripcion("Tratamiento ortodontico con aparatologia fija metalica para correccion de maloclusiones y alineacion dental.")
            .procedimiento("Se realiza estudio cefalometrico, se cementan los brackets y se planifican los arcos de tratamiento mensualmente.")
            .semanasEstimadas(96)
            .costoBaseTratamiento(1500)
            .notasAdicionales("Incluye la colocacion. Los controles mensuales tienen costo adicional.")
            .imagen_referencial("https://example.com/images/brackets-metalicos.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Ortodoncia con brackets de ceramica")
            .descripcion("Tratamiento ortodontico con brackets esteticos de ceramica menos visibles que los metalicos.")
            .procedimiento("Mismo protocolo que los metalicos con material de menor visibilidad estetica.")
            .semanasEstimadas(96)
            .costoBaseTratamiento(2000)
            .notasAdicionales("Mayor fragilidad que los metalicos. Se recomienda en pacientes adultos con demanda estetica.")
            .imagen_referencial("https://example.com/images/brackets-ceramica.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Ortodoncia con alineadores transparentes")
            .descripcion("Tratamiento con alineadores removibles de plastico transparente para correccion discreta de maloclusiones.")
            .procedimiento("Se realiza escaneo digital, se planifica el movimiento en software 3D y se fabrican los alineadores por etapas.")
            .semanasEstimadas(72)
            .costoBaseTratamiento(3000)
            .notasAdicionales("Requiere alta colaboracion del paciente. Se cambian cada 1 a 2 semanas.")
            .imagen_referencial("https://example.com/images/alineadores.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Retenedor de ortodoncia")
            .descripcion("Aparato fijo o removible para mantener la posicion dental tras finalizar el tratamiento ortodontico.")
            .procedimiento("Se toma impresion post-tratamiento y se confecciona el retenedor. Se cimenta o entrega para uso nocturno.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(200)
            .notasAdicionales("Uso indefinido recomendado para evitar recidiva.")
            .imagen_referencial("https://example.com/images/retenedor.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Ortopedia funcional maxilar")
            .descripcion("Tratamiento interceptivo en pacientes en crecimiento para corregir discrepancias esqueleticas.")
            .procedimiento("Se confecciona un aparato funcional (activador, twin-block, etc.) y se controla mensualmente.")
            .semanasEstimadas(52)
            .costoBaseTratamiento(1200)
            .notasAdicionales("Indicado en ninos de 7 a 12 anos. Puede evitar cirugia ortognatica futura.")
            .imagen_referencial("https://example.com/images/ortopedia.jpg")
            .build(),

        // ── OCLUSION Y BRUXISMO ───────────────────────────────────────────────
        TreatmentEntity.builder()
            .nombreTratamiento("Ferula oclusal")
            .descripcion("Placa de relajacion muscular para tratamiento de bruxismo, apretamiento dental y disfuncion temporomandibular.")
            .procedimiento("Se toman impresiones, se registra la oclusion en relacion centrica y se confecciona la ferula en laboratorio.")
            .semanasEstimadas(2)
            .costoBaseTratamiento(350)
            .notasAdicionales("Uso nocturno. Requiere ajuste y controles periodicos.")
            .imagen_referencial("https://example.com/images/ferula.jpg")
            .build(),
        TreatmentEntity.builder()
            .nombreTratamiento("Ajuste oclusal")
            .descripcion("Desgaste selectivo de superficies dentales para equilibrar la oclusion y eliminar interferencias.")
            .procedimiento("Se marcan los contactos con papel articular y se realizan desgastes controlados en los puntos de interferencia.")
            .semanasEstimadas(1)
            .costoBaseTratamiento(150)
            .notasAdicionales("Procedimiento irreversible. Requiere diagnostico previo riguroso.")
            .imagen_referencial("https://example.com/images/ajuste-oclusal.jpg")
            .build()
    );

    private final TreatmentRepository treatmentRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (TreatmentEntity treatment : DEFAULT_TREATMENTS) {
            if (treatmentRepository.existsByNombreTratamiento(treatment.getNombreTratamiento())) {
                log.info("Treatment verified: nombre='{}'", treatment.getNombreTratamiento());
            } else {
                TreatmentEntity saved = treatmentRepository.save(treatment);
                log.info("Treatment created: nombre='{}', id={}", saved.getNombreTratamiento(), saved.getId());
            }
        }
    }
}
