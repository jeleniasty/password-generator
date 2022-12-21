package com.jeleniasty.passwordgenerator.repository;

import com.jeleniasty.passwordgenerator.service.PasswordStrength;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "passwordgenerator")
public class Password {

    @Id
    @SequenceGenerator(name = "passwordgenerator.password_id_seq",
            sequenceName = "passwordgenerator.password_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "passwordgenerator.password_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private LocalDateTime dateCreated;
    private String password;
    private PasswordStrength strength;

    public Password(LocalDateTime dateCreated, String password, PasswordStrength strength) {
        this.dateCreated = dateCreated;
        this.password = password;
        this.strength = strength;
    }
}
