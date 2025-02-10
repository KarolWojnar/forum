package org.forum.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String role;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean isActivated;
}

