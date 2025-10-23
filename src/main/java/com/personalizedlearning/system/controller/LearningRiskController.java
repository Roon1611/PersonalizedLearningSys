package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.LearningRisk;
import com.personalizedlearning.system.service.LearningRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/risks")
public class LearningRiskController {

    @Autowired
    private LearningRiskService learningRiskService;

    // 根据ID查询学习风险
    @GetMapping("/{id}")
    public ResponseEntity<LearningRisk> getRiskById(@PathVariable Long id) {
        LearningRisk risk = learningRiskService.findById(id);
        if (risk != null) {
            return ResponseEntity.ok(risk);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据学生ID查询学习风险
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<LearningRisk>> getRisksByStudentId(@PathVariable Long studentId) {
        List<LearningRisk> risks = learningRiskService.findByStudentId(studentId);
        return ResponseEntity.ok(risks);
    }

    // 根据课程ID查询学习风险
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LearningRisk>> getRisksByCourseId(@PathVariable Long courseId) {
        List<LearningRisk> risks = learningRiskService.findByCourseId(courseId);
        return ResponseEntity.ok(risks);
    }

    // 根据学生和课程ID查询学习风险
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<LearningRisk> getRiskByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        LearningRisk risk = learningRiskService.findByStudentAndCourse(studentId, courseId);
        if (risk != null) {
            return ResponseEntity.ok(risk);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据风险等级查询
    @GetMapping("/level/{level}")
    public ResponseEntity<List<LearningRisk>> getRisksByLevel(@PathVariable String level) {
        List<LearningRisk> risks = learningRiskService.findByLevel(level);
        return ResponseEntity.ok(risks);
    }

    // 查询所有学习风险
    @GetMapping
    public ResponseEntity<List<LearningRisk>> getAllRisks() {
        List<LearningRisk> risks = learningRiskService.findAll();
        return ResponseEntity.ok(risks);
    }

    // 添加学习风险
    @PostMapping
    public ResponseEntity<LearningRisk> addRisk(@RequestBody LearningRisk risk) {
        int result = learningRiskService.insert(risk);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(risk);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新学习风险
    @PutMapping("/{id}")
    public ResponseEntity<LearningRisk> updateRisk(@PathVariable Long id, @RequestBody LearningRisk risk) {
        risk.setId(id);
        int result = learningRiskService.update(risk);
        if (result > 0) {
            return ResponseEntity.ok(risk);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除学习风险
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id) {
        int result = learningRiskService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 评估学习风险
    @PostMapping("/evaluate")
    public ResponseEntity<LearningRisk> evaluateLearningRisk(@RequestParam Long studentId, @RequestParam Long courseId) {
        LearningRisk risk = learningRiskService.evaluateLearningRisk(studentId, courseId);
        return ResponseEntity.ok(risk);
    }

    // 批量评估学习风险
    @PostMapping("/evaluate/batch")
    public ResponseEntity<Map<String, Object>> batchEvaluateLearningRisks(@RequestBody List<Map<String, Long>> studentCoursePairs) {
        int successCount = 0;
        for (Map<String, Long> pair : studentCoursePairs) {
            Long studentId = pair.get("studentId");
            Long courseId = pair.get("courseId");
            if (studentId != null && courseId != null) {
                learningRiskService.evaluateLearningRisk(studentId, courseId);
                successCount++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("total", studentCoursePairs.size());
        response.put("success", successCount);
        response.put("failed", studentCoursePairs.size() - successCount);

        return ResponseEntity.ok(response);
    }

    // 更新风险状态
    @PutMapping("/{id}/status")
    public ResponseEntity<LearningRisk> updateRiskStatus(@PathVariable Long id, @RequestParam String status) {
        LearningRisk risk = learningRiskService.findById(id);
        if (risk != null) {
            risk.setStatus(status);
            learningRiskService.update(risk);
            return ResponseEntity.ok(risk);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 获取风险统计信息
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getRiskStatistics() {
        LearningRiskService.RiskStatistics statistics = learningRiskService.getRiskStatistics();
        Map<String, Object> result = new HashMap<>();
        result.put("totalRisks", statistics.getTotalRisks());
        result.put("highRisks", statistics.getHighRisks());
        result.put("mediumRisks", statistics.getMediumRisks());
        result.put("lowRisks", statistics.getLowRisks());
        result.put("resolvedRisks", statistics.getResolvedRisks());
        result.put("activeRisks", statistics.getActiveRisks());
        return ResponseEntity.ok(result);
    }

    // 获取课程风险统计
    @GetMapping("/course/{courseId}/statistics")
    public ResponseEntity<Map<String, Object>> getRiskStatisticsByCourse(@PathVariable Long courseId) {
        LearningRiskService.RiskStatistics statistics = learningRiskService.getRiskStatisticsByCourseId(courseId);
        Map<String, Object> result = new HashMap<>();
        result.put("totalRisks", statistics.getTotalRisks());
        result.put("highRisks", statistics.getHighRisks());
        result.put("mediumRisks", statistics.getMediumRisks());
        result.put("lowRisks", statistics.getLowRisks());
        result.put("resolvedRisks", statistics.getResolvedRisks());
        result.put("activeRisks", statistics.getActiveRisks());
        return ResponseEntity.ok(result);
    }

    // 查询高风险学生
    @GetMapping("/high-risk/students")
    public ResponseEntity<List<Map<String, Object>>> getHighRiskStudents() {
        return ResponseEntity.ok(learningRiskService.getHighRiskStudents());
    }

    // 查询即将到期的风险预警
    @GetMapping("/expiring-soon")
    public ResponseEntity<List<LearningRisk>> getExpiringSoonRisks(@RequestParam(defaultValue = "7") int days) {
        List<LearningRisk> risks = learningRiskService.findExpiringSoonRisks(days);
        return ResponseEntity.ok(risks);
    }
}