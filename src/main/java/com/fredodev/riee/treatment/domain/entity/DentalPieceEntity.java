package com.fredodev.riee.treatment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dental_pieces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentalPieceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cuadrante")
    private String cuadrante;
}
