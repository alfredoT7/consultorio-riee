INSERT INTO public.treatments
(costo_base_tratamiento, descripcion, imagen_referencial, nombre_tratamiento, notas_adicionales, procedimiento, semanas_estimadas)
VALUES
    (150, 'Evaluacion clinica general de la cavidad oral para detectar caries, enfermedad periodontal y otras patologias.', 'https://example.com/images/evaluacion-oral.jpg', 'Evaluacion oral integral', 'Incluye revision inicial y recomendaciones generales.', 'Se realiza anamnesis, inspeccion clinica, odontograma y plan de tratamiento inicial.', 1),

    (120, 'Remocion de placa bacteriana, sarro y manchas superficiales para mejorar la salud gingival.', 'https://example.com/images/limpieza-dental.jpg', 'Limpieza dental', 'Recomendado cada 6 meses.', 'Se efectua profilaxis con ultrasonido, pulido dental y aplicacion de recomendaciones de higiene.', 1),

    (180, 'Tratamiento restaurador para dientes afectados por caries de pequena o mediana extension.', 'https://example.com/images/resina-dental.jpg', 'Obturacion con resina', 'El costo puede variar segun el numero de superficies.', 'Se elimina el tejido afectado, se desinfecta la cavidad y se reconstruye con resina compuesta.', 1),

    (350, 'Tratamiento endodontico para eliminar la infeccion pulpar y conservar la pieza dental.', 'https://example.com/images/endodoncia.jpg', 'Endodoncia unirradicular', 'Puede requerir radiografia de control.', 'Se accede al conducto, se instrumenta, desinfecta y obtura el sistema de conductos.', 2),

    (700, 'Tratamiento ortodontico para corregir alineacion dental y maloclusiones.', 'https://example.com/images/ortodoncia.jpg', 'Ortodoncia correctiva', 'No incluye controles mensuales adicionales.', 'Se realiza estudio previo, colocacion de brackets y plan de controles periodicos.', 24),

    (250, 'Extraccion de pieza dental con tecnica simple y manejo basico postoperatorio.', 'https://example.com/images/exodoncia-simple.jpg', 'Exodoncia simple', 'Incluye indicaciones postextraccion.', 'Se aplica anestesia local, se luxa la pieza y se realiza la extraccion con control de hemostasia.', 1),

    (500, 'Extraccion quirurgica de terceros molares o piezas retenidas con procedimiento especializado.', 'https://example.com/images/exodoncia-quirurgica.jpg', 'Exodoncia quirurgica', 'Puede requerir medicacion complementaria.', 'Se realiza colgajo, osteotomia si corresponde, extraccion y sutura.', 2),

    (220, 'Blanqueamiento dental para mejorar el color de las piezas dentarias vitales.', 'https://example.com/images/blanqueamiento.jpg', 'Blanqueamiento dental', 'Se recomienda evaluacion previa de sensibilidad.', 'Se protege tejido gingival y se aplica agente blanqueador segun protocolo clinico.', 2),

    (900, 'Rehabilitacion mediante corona fija para restaurar anatomia, funcion y estetica dental.', 'https://example.com/images/corona-dental.jpg', 'Corona dental de porcelana', 'Puede requerir provisional durante el proceso.', 'Se talla la pieza, se toma impresion, se coloca provisional y luego se cementa la corona definitiva.', 3),

    (280, 'Tratamiento periodontal basico para controlar gingivitis o periodontitis inicial.', 'https://example.com/images/periodoncia.jpg', 'Raspado y alisado radicular', 'Puede dividirse por cuadrantes.', 'Se realiza raspado subgingival, alisado radicular y control de inflamacion periodontal.', 2);