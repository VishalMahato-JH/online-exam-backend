package com.exam.online_exam_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponse {

    private int totalQuestions;

    private int correctAnswers;

    private int score;
}