package com.exam.online_exam_system.service;

import com.exam.online_exam_system.entity.Exam;
import com.exam.online_exam_system.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public Exam createExam(Exam exam) {

        return examRepository.save(exam);
    }

    public Optional<Exam> getActiveExam() {

        LocalDateTime now = LocalDateTime.now();

        return examRepository
                .findByStartTimeBeforeAndEndTimeAfter(now, now);
    }

    public List<Exam> getAllExams() {

        return examRepository.findAll();
    }
    public Exam getExamById(Long examId) {

        return examRepository
                .findById(examId)
                .orElseThrow();
    }
    public void deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow();

        exam.getQuestions().clear();

        examRepository.save(exam);

        examRepository.delete(exam);
    }

    public Exam updateExam(
            Long id,
            Exam updatedExam
    ) {

        Exam exam =
                examRepository.findById(id)
                        .orElseThrow();

        exam.setTitle(updatedExam.getTitle());

        exam.setDescription(updatedExam.getDescription());

        exam.setDuration(updatedExam.getDuration());

        exam.setTotalMarks(updatedExam.getTotalMarks());

        exam.setStartTime(updatedExam.getStartTime());

        exam.setEndTime(updatedExam.getEndTime());

        return examRepository.save(exam);
    }
}