package com.exam.online_exam_system.repository;

import com.exam.online_exam_system.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByStartTimeBeforeAndEndTimeAfter(
            LocalDateTime now1,
            LocalDateTime now2
    );
}