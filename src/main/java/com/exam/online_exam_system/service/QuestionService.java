package com.exam.online_exam_system.service;

import com.exam.online_exam_system.dto.AnswerRequest;
import com.exam.online_exam_system.dto.ResultResponse;
import com.exam.online_exam_system.entity.Question;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface QuestionService {

    Question addQuestion(
            Long examId,
            Question question
    );

    List<Question> getQuestionsByExam(
            Long examId
    );

    void deleteQuestion(Long id);

    void uploadQuestions(Long examId, MultipartFile file);

    Question updateQuestion(
            Long id,
            Question question
    );

    ResultResponse submitExam(
            List<AnswerRequest> answers
    );
}