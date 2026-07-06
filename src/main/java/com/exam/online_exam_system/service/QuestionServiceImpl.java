package com.exam.online_exam_system.service;

import com.exam.online_exam_system.dto.AnswerRequest;
import com.exam.online_exam_system.dto.ResultResponse;
import com.exam.online_exam_system.entity.Exam;
import com.exam.online_exam_system.entity.Question;
import com.exam.online_exam_system.repository.ExamRepository;
import com.exam.online_exam_system.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamRepository examRepository;

    @Override
    public Question addQuestion(Long examId, Question question) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow();

        question.setExam(exam);

        return questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestionsByExam(Long examId) {

        return questionRepository.findByExamId(examId);
    }

    @Override
    public void deleteQuestion(Long id) {

        questionRepository.deleteById(id);
    }

    @Override
    public Question updateQuestion(Long id, Question updatedQuestion) {

        Question question = questionRepository.findById(id)
                .orElseThrow();

        question.setQuestionText(updatedQuestion.getQuestionText());

        question.setOption1(updatedQuestion.getOption1());

        question.setOption2(updatedQuestion.getOption2());

        question.setOption3(updatedQuestion.getOption3());

        question.setOption4(updatedQuestion.getOption4());

        question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());

        question.setSectionName(updatedQuestion.getSectionName());

        question.setMarks(updatedQuestion.getMarks());

        return questionRepository.save(question);
    }

    @Override
    public ResultResponse submitExam(List<AnswerRequest> answers) {

        return null;
    }

    @Override
    public void uploadQuestions(Long examId, MultipartFile file) {

        try {

            Exam exam = examRepository
                    .findById(examId)
                    .orElseThrow();

            Workbook workbook =
                    new XSSFWorkbook(file.getInputStream());

            Sheet sheet =
                    workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                Question question = new Question();

                question.setQuestionText(
                        row.getCell(0).getStringCellValue());

                question.setOption1(
                        row.getCell(1).getStringCellValue());

                question.setOption2(
                        row.getCell(2).getStringCellValue());

                question.setOption3(
                        row.getCell(3).getStringCellValue());

                question.setOption4(
                        row.getCell(4).getStringCellValue());

                question.setCorrectAnswer(
                        row.getCell(5).getStringCellValue());

                question.setSectionName(
                        row.getCell(6).getStringCellValue());

                question.setMarks(
                        (int) row.getCell(7).getNumericCellValue());

                question.setExam(exam);

                questionRepository.save(question);
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UPLOAD ERROR: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}