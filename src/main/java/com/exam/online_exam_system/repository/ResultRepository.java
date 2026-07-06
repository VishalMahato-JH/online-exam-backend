package com.exam.online_exam_system.repository;

import com.exam.online_exam_system.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentEmail(String studentEmail);

    List<Result> findAllByOrderByScoreDesc();

    Optional<Result> findByStudentEmailAndExamId(
            String studentEmail,
            Long examId
    );
    boolean existsByStudentEmailAndExamId(
            String studentEmail,
            Long examId
    );

    Result findTopByStudentEmailAndExamIdOrderByIdDesc(
            String studentEmail,
            Long examId
    );
    @Query("SELECT COUNT(r) FROM Result r WHERE r.score >= (r.totalQuestions * 0.4)")
    long countPassed();

    @Query("SELECT COUNT(r) FROM Result r WHERE r.score < (r.totalQuestions * 0.4)")
    long countFailed();


    List<Result> findTop10ByOrderBySubmittedAtDesc();
}