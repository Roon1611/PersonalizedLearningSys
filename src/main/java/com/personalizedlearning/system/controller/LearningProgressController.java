package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.LearningProgress;
import com.personalizedlearning.system.service.LearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
public class LearningProgressController {

    @Autowired
    private LearningProgressService learningProgressService;

    // 根据ID查询学习进度
    @GetMapping("/{id}")
    public ResponseEntity<LearningProgress> getProgressById(@PathVariable Long id) {
        LearningProgress progress = learningProgressService.findById(id);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据学生ID和课程ID查询学习进度
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<LearningProgress> getProgressByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        LearningProgress progress = learningProgressService.findByStudentIdAndCourseId(studentId, courseId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据学生ID查询所有学习进度
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<LearningProgress>> getProgressByStudentId(@PathVariable Long studentId) {
        List<LearningProgress> progresses = learningProgressService.findByStudentId(studentId);
        return ResponseEntity.ok(progresses);
    }

    // 根据课程ID查询所有学习进度
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LearningProgress>> getProgressByCourseId(@PathVariable Long courseId) {
        List<LearningProgress> progresses = learningProgressService.findByCourseId(courseId);
        return ResponseEntity.ok(progresses);
    }

    // 查询所有学习进度
    @GetMapping
    public ResponseEntity<List<LearningProgress>> getAllProgresses() {
        List<LearningProgress> progresses = learningProgressService.findAll();
        return ResponseEntity.ok(progresses);
    }

    // 添加学习进度
    @PostMapping
    public ResponseEntity<LearningProgress> addProgress(@RequestBody LearningProgress progress) {
        int result = learningProgressService.insert(progress);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(progress);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新学习进度
    @PutMapping("/{id}")
    public ResponseEntity<LearningProgress> updateProgress(@PathVariable Long id, @RequestBody LearningProgress progress) {
        progress.setId(id);
        int result = learningProgressService.update(progress);
        if (result > 0) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除学习进度
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        int result = learningProgressService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新学习进度的完成度
    @PutMapping("/{id}/completion")
    public ResponseEntity<LearningProgress> updateProgressCompletion(@PathVariable Long id, @RequestParam Double completionRate) {
        LearningProgress progress = learningProgressService.findById(id);
        if (progress != null) {
            progress.setCompletionRate(completionRate);
            learningProgressService.update(progress);
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 计算课程平均完成度
    @GetMapping("/course/{courseId}/average-completion")
    public ResponseEntity<Map<String, Double>> calculateAverageCompletionByCourse(@PathVariable Long courseId) {
        double average = learningProgressService.calculateAverageCompletionRateByCourseId(courseId);
        Map<String, Double> result = new HashMap<>();
        result.put("averageCompletionRate", average);
        return ResponseEntity.ok(result);
    }

    // 计算学生平均完成度
    @GetMapping("/student/{studentId}/average-completion")
    public ResponseEntity<Map<String, Double>> calculateAverageCompletionByStudent(@PathVariable Long studentId) {
        double average = learningProgressService.calculateAverageCompletionRateByStudentId(studentId);
        Map<String, Double> result = new HashMap<>();
        result.put("averageCompletionRate", average);
        return ResponseEntity.ok(result);
    }

    // 获取学习进度统计信息
    @GetMapping("/course/{courseId}/statistics")
    public ResponseEntity<Map<String, Object>> getProgressStatisticsByCourse(@PathVariable Long courseId) {
        LearningProgressService.ProgressStatistics statistics = learningProgressService.getProgressStatisticsByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        result.put("averageCompletionRate", statistics.getAverageCompletionRate());
        result.put("highestCompletionRate", statistics.getHighestCompletionRate());
        result.put("lowestCompletionRate", statistics.getLowestCompletionRate());
        result.put("medianCompletionRate", statistics.getMedianCompletionRate());
        result.put("completedStudents", statistics.getCompletedStudents());
        result.put("completionRatePercentage", statistics.getCompletionRatePercentage());
        return ResponseEntity.ok(result);
    }

    // 查询学习进度低于指定阈值的学生
    @GetMapping("/course/{courseId}/below-threshold")
    public ResponseEntity<List<LearningProgress>> getProgressBelowThreshold(@PathVariable Long courseId, @RequestParam(defaultValue = "60.0") Double threshold) {
        List<LearningProgress> progresses = learningProgressService.findByCompletionRateBelow(courseId, threshold);
        return ResponseEntity.ok(progresses);
    }

    // 批量更新学习进度
    @PutMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchUpdateProgresses(@RequestBody List<LearningProgress> progresses) {
        int successCount = 0;
        for (LearningProgress progress : progresses) {
            if (learningProgressService.update(progress) > 0) {
                successCount++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("total", progresses.size());
        response.put("success", successCount);
        response.put("failed", progresses.size() - successCount);

        return ResponseEntity.ok(response);
    }

    // 获取模拟进度数据
    @GetMapping("/mock-data")
    public ResponseEntity<List<LearningProgress>> getMockProgressData() {
        List<LearningProgress> mockData = learningProgressService.getMockProgressData();
        return ResponseEntity.ok(mockData);
    }
}