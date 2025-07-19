package com.fredodev.riee.dentist.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "dentists")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class DentistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private Long telefono;

    @Column(nullable = false)
    private String apellidos;

    @Column(unique = true)
    private Long ciDentista;

    private String universidad;
    private Long promocion;
    private String imagenUrl;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL)
    private List<DentistSpecialityEntity> specialities;
}
