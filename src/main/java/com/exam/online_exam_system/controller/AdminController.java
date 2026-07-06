package com.exam.online_exam_system.controller;

import com.exam.online_exam_system.repository.ExamRepository;
import com.exam.online_exam_system.repository.QuestionRepository;
import com.exam.online_exam_system.repository.ResultRepository;
import com.exam.online_exam_system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {

        Map<String, Object> analytics = new HashMap<>();

        analytics.put(
                "totalStudents",
                userRepository.countByRole(
                        "STUDENT"
                )
        );

        analytics.put(
                "totalExams",
                examRepository.count()
        );

        analytics.put(
                "totalQuestions",
                questionRepository.count()
        );

        analytics.put(
                "totalResults",
                resultRepository.count()
        );

        double averageScore = resultRepository
                .findAll()
                .stream()
                .mapToInt(result -> result.getScore())
                .average()
                .orElse(0);

        analytics.put(
                "averageScore",
                averageScore
        );

        analytics.put(
                "passed",
                resultRepository.countPassed()
        );

        analytics.put(
                "failed",
                resultRepository.countFailed()
        );

        return analytics;
    }
}