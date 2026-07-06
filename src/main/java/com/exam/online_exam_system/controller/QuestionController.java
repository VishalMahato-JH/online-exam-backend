package com.exam.online_exam_system.controller;

import com.exam.online_exam_system.entity.Question;
import com.exam.online_exam_system.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.exam.online_exam_system.service.QuestionService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;


    @GetMapping
    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {

        return questionRepository.save(question);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(
            @PathVariable Long id,
            @RequestBody Question updatedQuestion
    ) {

        Question question = questionRepository.findById(id).orElseThrow();

        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setOption1(updatedQuestion.getOption1());
        question.setOption2(updatedQuestion.getOption2());
        question.setOption3(updatedQuestion.getOption3());
        question.setOption4(updatedQuestion.getOption4());
        question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        question.setMarks(updatedQuestion.getMarks());

        return questionRepository.save(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {

        questionRepository.deleteById(id);
    }

    @GetMapping("/exam/{examId}")
    public List<Question> getQuestionsByExam(
            @PathVariable Long examId
    ) {

        return questionRepository.findByExamId(examId);
    }

    @PostMapping("/upload/{examId}")
    public ResponseEntity<String> uploadQuestions(
            @PathVariable Long examId,
            @RequestParam("file") MultipartFile file) {

        questionService.uploadQuestions(
                examId,
                file
        );

        return ResponseEntity.ok(
                "Questions Uploaded Successfully"
        );
    }
}