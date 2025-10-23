package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.Grade;
import com.personalizedlearning.system.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // 根据ID查询成绩
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        Grade grade = gradeService.findById(id);
        if (grade != null) {
            return ResponseEntity.ok(grade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据学生ID查询成绩
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getGradesByStudentId(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.findByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    // 根据课程ID查询成绩
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Grade>> getGradesByCourseId(@PathVariable Long courseId) {
        List<Grade> grades = gradeService.findByCourseId(courseId);
        return ResponseEntity.ok(grades);
    }

    // 根据学生和课程ID查询成绩
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> getGradeByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        Grade grade = gradeService.findByStudentAndCourse(studentId, courseId);
        if (grade != null) {
            return ResponseEntity.ok(grade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查询所有成绩
    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }

    // 添加成绩
    @PostMapping
    public ResponseEntity<Grade> addGrade(@RequestBody Grade grade) {
        int result = gradeService.insert(grade);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(grade);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新成绩
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        grade.setId(id);
        int result = gradeService.update(grade);
        if (result > 0) {
            return ResponseEntity.ok(grade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除成绩
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        int result = gradeService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 计算课程平均分
    @GetMapping("/course/{courseId}/average")
    public ResponseEntity<Map<String, Double>> calculateAverageScoreByCourse(@PathVariable Long courseId) {
        double average = gradeService.calculateAverageScoreByCourseId(courseId);
        Map<String, Double> result = new HashMap<>();
        result.put("averageScore", average);
        return ResponseEntity.ok(result);
    }

    // 计算学生平均分
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<Map<String, Double>> calculateAverageScoreByStudent(@PathVariable Long studentId) {
        double average = gradeService.calculateAverageScoreByStudentId(studentId);
        Map<String, Double> result = new HashMap<>();
        result.put("averageScore", average);
        return ResponseEntity.ok(result);
    }

    // 获取课程最高分
    @GetMapping("/course/{courseId}/highest")
    public ResponseEntity<Map<String, Double>> getHighestScoreByCourse(@PathVariable Long courseId) {
        double highest = gradeService.findHighestScoreByCourseId(courseId);
        Map<String, Double> result = new HashMap<>();
        result.put("highestScore", highest);
        return ResponseEntity.ok(result);
    }

    // 获取课程最低分
    @GetMapping("/course/{courseId}/lowest")
    public ResponseEntity<Map<String, Double>> getLowestScoreByCourse(@PathVariable Long courseId) {
        double lowest = gradeService.findLowestScoreByCourseId(courseId);
        Map<String, Double> result = new HashMap<>();
        result.put("lowestScore", lowest);
        return ResponseEntity.ok(result);
    }

    // 获取课程成绩统计
    @GetMapping("/course/{courseId}/statistics")
    public ResponseEntity<Map<String, Object>> getGradeStatisticsByCourse(@PathVariable Long courseId) {
        GradeService.GradeStatistics statistics = gradeService.getGradeStatisticsByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        result.put("averageScore", statistics.getAverageScore());
        result.put("highestScore", statistics.getHighestScore());
        result.put("lowestScore", statistics.getLowestScore());
        result.put("medianScore", statistics.getMedianScore());
        result.put("passedStudents", statistics.getPassedStudents());
        result.put("passRate", statistics.getPassRate());
        return ResponseEntity.ok(result);
    }

    // 获取成绩分布
    @GetMapping("/course/{courseId}/distribution")
    public ResponseEntity<Map<String, Integer>> getGradeDistributionByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.getGradeDistributionByCourseId(courseId));
    }

    // 批量添加成绩
    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchAddGrades(@RequestBody List<Grade> grades) {
        int successCount = 0;
        for (Grade grade : grades) {
            if (gradeService.insert(grade) > 0) {
                successCount++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("total", grades.size());
        response.put("success", successCount);
        response.put("failed", grades.size() - successCount);

        return ResponseEntity.ok(response);
    }
}