package com.exam.online_exam_system.controller;

import com.exam.online_exam_system.entity.Exam;
import com.exam.online_exam_system.service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {

        return examService.createExam(exam);
    }

    @GetMapping
    public List<Exam> getAllExams() {

        return examService.getAllExams();
    }

    @DeleteMapping("/{id}")
    public String deleteExam(
            @PathVariable Long id
    ) {

        examService.deleteExam(id);

        return "Exam Deleted Successfully";
    }

    @PutMapping("/{id}")
    public Exam updateExam(
            @PathVariable Long id,
            @RequestBody Exam updatedExam
    ) {

        return examService.updateExam(id, updatedExam);
    }

    @GetMapping("/active")
    public Optional<Exam> getActiveExam() {

        return examService.getActiveExam();
    }

    @GetMapping("/remaining-time/{examId}")
    public Map<String, Object> getRemainingTime(
            @PathVariable Long examId
    ) {

        Exam exam = examService
                .getExamById(examId);

        LocalDateTime now = LocalDateTime.now();

        Duration duration =
                Duration.between(now, exam.getEndTime());

        long totalSeconds =
                Math.max(0, duration.getSeconds());

        long minutesLeft = totalSeconds / 60;

        long secondsLeft = totalSeconds % 60;

        boolean examEnded = totalSeconds == 0;

        Map<String, Object> response = new HashMap<>();

        response.put("examId", examId);

        response.put("minutesLeft", minutesLeft);

        response.put("secondsLeft", secondsLeft);

        response.put("examEnded", examEnded);

        return response;
    }
}