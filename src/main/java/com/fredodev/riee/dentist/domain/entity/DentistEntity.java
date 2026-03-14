package com.fredodev.riee.dentist.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dentists")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private Long telefono;
    private Long ciDentista;
    private String universidad;
    private Long promocion;
    private String imagenUrl;
    private String imagenPublicId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "dentist_speciality",
            joinColumns = @JoinColumn(name = "dentist_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityEntity> specialities = new ArrayList<>();

    public void addSpeciality(SpecialityEntity speciality) {
        specialities.add(speciality);
        // speciality.getDentists().add(this);
    }

    public void removeSpeciality(SpecialityEntity speciality) {
        specialities.remove(speciality);
        // speciality.getDentists().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_DENTIST"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
