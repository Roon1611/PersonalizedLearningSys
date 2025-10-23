package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.Course;
import com.personalizedlearning.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 根据ID查询课程
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查询所有课程
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    // 根据教师ID查询课程
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        List<Course> courses = courseService.findByTeacherId(teacherId);
        return ResponseEntity.ok(courses);
    }

    // 根据学生ID查询课程
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable Long studentId) {
        List<Course> courses = courseService.findByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }

    // 添加课程
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        int result = courseService.insert(course);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 更新课程
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        int result = courseService.update(course);
        if (result > 0) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 删除课程
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        int result = courseService.delete(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 学生加入课程
    @PostMapping("/{courseId}/join")
    public ResponseEntity<Map<String, Object>> joinCourse(@PathVariable Long courseId, @RequestParam Long studentId) {
        boolean result = courseService.addStudentToCourse(studentId, courseId);
        Map<String, Object> response = new HashMap<>();

        if (result) {
            response.put("success", true);
            response.put("message", "加入课程成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "加入课程失败，可能已在课程中");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 学生退出课程
    @DeleteMapping("/{courseId}/leave")
    public ResponseEntity<Map<String, Object>> leaveCourse(@PathVariable Long courseId, @RequestParam Long studentId) {
        boolean result = courseService.removeStudentFromCourse(studentId, courseId);
        Map<String, Object> response = new HashMap<>();

        if (result) {
            response.put("success", true);
            response.put("message", "退出课程成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "退出课程失败，可能不在课程中");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 检查学生是否已加入课程
    @GetMapping("/{courseId}/check-student")
    public ResponseEntity<Map<String, Object>> checkStudentCourseRelation(@PathVariable Long courseId, @RequestParam Long studentId) {
        boolean isInCourse = courseService.checkStudentCourseRelation(studentId, courseId);
        Map<String, Object> response = new HashMap<>();
        response.put("isInCourse", isInCourse);
        return ResponseEntity.ok(response);
    }

    // 搜索课程
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String keyword) {
        List<Course> courses = courseService.searchCourses(keyword);
        return ResponseEntity.ok(courses);
    }
}