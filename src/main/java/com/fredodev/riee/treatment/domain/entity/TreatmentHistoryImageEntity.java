package com.fredodev.riee.treatment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_history_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "treatment_history_id")
    private TreatmentHistoryEntity treatmentHistory;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
