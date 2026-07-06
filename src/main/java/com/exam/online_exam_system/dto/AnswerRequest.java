package com.exam.online_exam_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {

    private Long questionId;

    private String selectedAnswer;
}