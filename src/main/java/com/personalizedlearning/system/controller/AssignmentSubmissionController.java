package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.AssignmentSubmission;
import com.personalizedlearning.system.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;

    // 根据ID查询作业提交
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentSubmission> getSubmissionById(@PathVariable Long id) {
        AssignmentSubmission submission = assignmentSubmissionService.findById(id);
        if (submission != null) {
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据作业ID查询提交
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsByAssignmentId(@PathVariable Long assignmentId) {
        List<AssignmentSubmission> submissions = assignmentSubmissionService.findByAssignmentId(assignmentId);
        return ResponseEntity.ok(submissions);
    }

    // 根据学生ID查询提交
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsByStudentId(@PathVariable Long studentId) {
        List<AssignmentSubmission> submissions = assignmentSubmissionService.findByStudentId(studentId);
        return ResponseEntity.ok(submissions);
    }

    // 根据学生和作业ID查询提交
    @GetMapping("/student/{studentId}/assignment/{assignmentId}")
    public ResponseEntity<AssignmentSubmission> getSubmissionByStudentAndAssignment(@PathVariable Long studentId, @PathVariable Long assignmentId) {
        AssignmentSubmission submission = assignmentSubmissionService.findByStudentAndAssignment(studentId, assignmentId);
        if (submission != null) {
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 提交作业
    @PostMapping
    public ResponseEntity<AssignmentSubmission> submitAssignment(@RequestBody AssignmentSubmission submission) {
        boolean isLate = assignmentSubmissionService.checkLateSubmission(submission.getAssignmentId());
        if (isLate) {
            submission.setStatus("LATE");
        } else {
            submission.setStatus("SUBMITTED");
        }
        int result = assignmentSubmissionService.insert(submission);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(submission);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新作业提交
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentSubmission> updateSubmission(@PathVariable Long id, @RequestBody AssignmentSubmission submission) {
        submission.setId(id);
        int result = assignmentSubmissionService.update(submission);
        if (result > 0) {
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除作业提交
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        int result = assignmentSubmissionService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 评分作业
    @PutMapping("/{id}/grade")
    public ResponseEntity<AssignmentSubmission> gradeSubmission(@PathVariable Long id, @RequestParam Double score, @RequestParam(required = false) String feedback) {
        AssignmentSubmission submission = assignmentSubmissionService.findById(id);
        if (submission != null) {
            submission.setScore(score);
            submission.setFeedback(feedback);
            submission.setStatus("GRADED");
            assignmentSubmissionService.update(submission);
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查询未评分的提交
    @GetMapping("/ungraded")
    public ResponseEntity<List<AssignmentSubmission>> getUngradedSubmissions() {
        List<AssignmentSubmission> submissions = assignmentSubmissionService.findUngradedSubmissions();
        return ResponseEntity.ok(submissions);
    }

    // 根据提交状态查询
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssignmentSubmission>> getSubmissionsByStatus(@PathVariable String status) {
        List<AssignmentSubmission> submissions = assignmentSubmissionService.findByStatus(status);
        return ResponseEntity.ok(submissions);
    }

    // 查询逾期提交
    @GetMapping("/late")
    public ResponseEntity<List<AssignmentSubmission>> getLateSubmissions() {
        List<AssignmentSubmission> submissions = assignmentSubmissionService.findLateSubmissions();
        return ResponseEntity.ok(submissions);
    }

    // 获取提交统计信息
    @GetMapping("/assignment/{assignmentId}/stats")
    public ResponseEntity<Map<String, Object>> getSubmissionStats(@PathVariable Long assignmentId) {
        int totalStudents = assignmentSubmissionService.getTotalStudentsByAssignment(assignmentId);
        int submittedCount = assignmentSubmissionService.getSubmittedCountByAssignment(assignmentId);
        int lateCount = assignmentSubmissionService.getLateCountByAssignment(assignmentId);
        double averageScore = assignmentSubmissionService.getAverageScoreByAssignment(assignmentId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", totalStudents);
        stats.put("submittedCount", submittedCount);
        stats.put("lateCount", lateCount);
        stats.put("averageScore", averageScore);
        stats.put("submissionRate", totalStudents > 0 ? (double) submittedCount / totalStudents * 100 : 0);

        return ResponseEntity.ok(stats);
    }

    // 批量提交作业
    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchSubmitAssignments(@RequestBody List<AssignmentSubmission> submissions) {
        int successCount = 0;
        for (AssignmentSubmission submission : submissions) {
            boolean isLate = assignmentSubmissionService.checkLateSubmission(submission.getAssignmentId());
            if (isLate) {
                submission.setStatus("LATE");
            } else {
                submission.setStatus("SUBMITTED");
            }
            if (assignmentSubmissionService.insert(submission) > 0) {
                successCount++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("total", submissions.size());
        response.put("success", successCount);
        response.put("failed", submissions.size() - successCount);

        return ResponseEntity.ok(response);
    }
}