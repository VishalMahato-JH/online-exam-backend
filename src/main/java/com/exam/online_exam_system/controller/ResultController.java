package com.exam.online_exam_system.controller;

import com.exam.online_exam_system.entity.Result;
import com.exam.online_exam_system.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.exam.online_exam_system.entity.Exam;
import com.exam.online_exam_system.repository.ExamRepository;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private ExamRepository examRepository;

    @GetMapping("/my-results/{email}")
    public List<Result> getMyResults(@PathVariable String email) {

        return resultRepository.findByStudentEmail(email);
    }
    @GetMapping("/leaderboard")
    public List<Result> leaderboard() {

        return resultRepository.findAllByOrderByScoreDesc();
    }
    @PostMapping("/submit")
    public Result submitResult(@RequestBody Result result) {

        boolean alreadyAttempted =
                resultRepository.existsByStudentEmailAndExamId(
                        result.getStudentEmail(),
                        result.getExam().getId()
                );

        if (alreadyAttempted) {

            throw new RuntimeException(
                    "You have already attempted this exam"
            );
        }
        Exam exam = examRepository
                .findById(result.getExam().getId())
                .orElseThrow();

        double percentage =
                ((double) result.getScore()
                        / exam.getTotalMarks()) * 100;

        result.setPercentage(percentage);

        if (percentage >= 40) {
            result.setStatus("PASS");
        } else {
            result.setStatus("FAIL");
        }

        return resultRepository.save(result);
    }
    @GetMapping("/attempted/{email}/{examId}")
    public boolean hasAttempted(
            @PathVariable String email,
            @PathVariable Long examId
    ) {

        return resultRepository
                .existsByStudentEmailAndExamId(
                        email,
                        examId
                );
    }
    @GetMapping("/latest/{email}/{examId}")
    public Result latestResult(
            @PathVariable String email,
            @PathVariable Long examId
    ) {

        return resultRepository
                .findTopByStudentEmailAndExamIdOrderByIdDesc(
                        email,
                        examId
                );
    }

    @GetMapping("/recent")
    public List<Result> getRecentResults() {

        return resultRepository
                .findTop10ByOrderBySubmittedAtDesc();
    }
}