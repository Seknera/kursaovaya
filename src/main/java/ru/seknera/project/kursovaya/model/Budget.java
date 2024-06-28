package ru.seknera.project.kursovaya.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @SequenceGenerator(sequenceName = "budget_id_seq", name = "budget_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_id_seq")
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

