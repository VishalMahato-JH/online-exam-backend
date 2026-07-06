package com.exam.online_exam_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;

    private Integer totalQuestions;

    private Integer correctAnswers;

    private Integer score;

    private Double percentage;

    private String status;

    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Exam exam;
}