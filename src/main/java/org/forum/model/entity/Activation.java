package org.forum.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.forum.model.ActivationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activation")
@NoArgsConstructor
@Data
public class Activation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String activationCode;
    @OneToOne
    @Setter
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private ActivationType type;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        expiresAt = createdAt.plusDays(1);
        activationCode = String.valueOf(UUID.randomUUID());
    }

    public Activation(ActivationType type) {
        this.type = type;
    }
}
