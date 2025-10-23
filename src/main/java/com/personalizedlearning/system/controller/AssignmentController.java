package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.Assignment;
import com.personalizedlearning.system.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // 根据ID查询作业
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Assignment assignment = assignmentService.findById(id);
        if (assignment != null) {
            return ResponseEntity.ok(assignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据课程ID查询作业
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourseId(@PathVariable Long courseId) {
        List<Assignment> assignments = assignmentService.findByCourseId(courseId);
        return ResponseEntity.ok(assignments);
    }

    // 查询学生待提交作业
    @GetMapping("/student/{studentId}/pending")
    public ResponseEntity<List<Assignment>> getPendingAssignmentsByStudentId(@PathVariable Long studentId) {
        List<Assignment> assignments = assignmentService.findPendingAssignmentsByStudentId(studentId);
        return ResponseEntity.ok(assignments);
    }

    // 查询学生已提交作业
    @GetMapping("/student/{studentId}/submitted")
    public ResponseEntity<List<Assignment>> getSubmittedAssignmentsByStudentId(@PathVariable Long studentId) {
        List<Assignment> assignments = assignmentService.findSubmittedAssignmentsByStudentId(studentId);
        return ResponseEntity.ok(assignments);
    }

    // 查询所有作业
    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.findAll();
        return ResponseEntity.ok(assignments);
    }

    // 添加作业
    @PostMapping
    public ResponseEntity<Assignment> addAssignment(@RequestBody Assignment assignment) {
        int result = assignmentService.insert(assignment);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新作业
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        assignment.setId(id);
        int result = assignmentService.update(assignment);
        if (result > 0) {
            return ResponseEntity.ok(assignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除作业
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        int result = assignmentService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据截止日期查询作业
    @GetMapping("/deadline")
    public ResponseEntity<List<Assignment>> getAssignmentsByDeadline(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<Assignment> assignments = assignmentService.findByDeadline(startDate, endDate);
        return ResponseEntity.ok(assignments);
    }

    // 更新作业状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateAssignmentStatus(@PathVariable Long id, @RequestParam String status) {
        int result = assignmentService.updateStatus(id, status);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据教师ID查询作业
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacherId(@PathVariable Long teacherId) {
        List<Assignment> assignments = assignmentService.findByTeacherId(teacherId);
        return ResponseEntity.ok(assignments);
    }

    // 查询即将截止的作业
    @GetMapping("/upcoming")
    public ResponseEntity<List<Assignment>> getUpcomingAssignments(@RequestParam(defaultValue = "7") int days) {
        List<Assignment> assignments = assignmentService.findUpcomingAssignments(days);
        return ResponseEntity.ok(assignments);
    }
}