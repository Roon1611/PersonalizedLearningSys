package com.personalizedlearning.system.controller;

import com.personalizedlearning.system.entity.StudentCourse;
import com.personalizedlearning.system.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生课程关系控制器
 */
@RestController
@RequestMapping("/api/student-courses")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    /**
     * 添加学生课程关系（学生报名课程）
     */
    @PostMapping
    public Map<String, Object> addStudentCourse(@RequestBody StudentCourse studentCourse) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 检查是否已报名
            Boolean isEnrolled = studentCourseService.isStudentEnrolled(studentCourse.getStudentId(), studentCourse.getCourseId());
            if (isEnrolled) {
                result.put("success", false);
                result.put("message", "该学生已经报名了这门课程");
                return result;
            }
            int count = studentCourseService.addStudentCourse(studentCourse);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "报名成功" : "报名失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "报名失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新学生课程关系
     */
    @PutMapping
    public Map<String, Object> updateStudentCourse(@RequestBody StudentCourse studentCourse) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.updateStudentCourse(studentCourse);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除学生课程关系
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteStudentCourse(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.deleteStudentCourse(id);
            result.put("success", count > 0);
            result.put("message", count > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据ID获取学生课程关系
     */
    @GetMapping("/{id}")
    public Map<String, Object> getStudentCourseById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            StudentCourse studentCourse = studentCourseService.getStudentCourseById(id);
            result.put("success", studentCourse != null);
            result.put("data", studentCourse);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有学生课程关系
     */
    @GetMapping
    public Map<String, Object> getAllStudentCourses() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<StudentCourse> studentCourses = studentCourseService.getAllStudentCourses();
            result.put("success", true);
            result.put("data", studentCourses);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据学生ID获取课程
     */
    @GetMapping("/student/{studentId}")
    public Map<String, Object> getCoursesByStudentId(@PathVariable Long studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<StudentCourse> studentCourses = studentCourseService.getCoursesByStudentId(studentId);
            result.put("success", true);
            result.put("data", studentCourses);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据课程ID获取学生
     */
    @GetMapping("/course/{courseId}")
    public Map<String, Object> getStudentsByCourseId(@PathVariable Long courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<StudentCourse> studentCourses = studentCourseService.getStudentsByCourseId(courseId);
            result.put("success", true);
            result.put("data", studentCourses);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 检查学生是否已报名课程
     */
    @GetMapping("/enrolled/{studentId}/{courseId}")
    public Map<String, Object> isStudentEnrolled(@PathVariable Long studentId, @PathVariable Long courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Boolean isEnrolled = studentCourseService.isStudentEnrolled(studentId, courseId);
            result.put("success", true);
            result.put("isEnrolled", isEnrolled);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取学生的课程数量
     */
    @GetMapping("/count/student/{studentId}")
    public Map<String, Object> countStudentCourses(@PathVariable Long studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.countStudentCourses(studentId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取课程的学生数量
     */
    @GetMapping("/count/course/{courseId}")
    public Map<String, Object> countCourseStudents(@PathVariable Long courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.countCourseStudents(courseId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取学生活跃课程数量
     */
    @GetMapping("/count/student/active/{studentId}")
    public Map<String, Object> countActiveStudentCourses(@PathVariable Long studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.countActiveStudentCourses(studentId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取课程活跃学生数量
     */
    @GetMapping("/count/course/active/{courseId}")
    public Map<String, Object> countActiveCourseStudents(@PathVariable Long courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = studentCourseService.countActiveCourseStudents(courseId);
            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取报名统计信息
     */
    @GetMapping("/stats")
    public Map<String, Object> getEnrollmentStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            int totalEnrollments = studentCourseService.getTotalEnrollments();
            int totalActiveEnrollments = studentCourseService.getTotalActiveEnrollments();
            Map<String, Integer> stats = new HashMap<>();
            stats.put("totalEnrollments", totalEnrollments);
            stats.put("totalActiveEnrollments", totalActiveEnrollments);
            result.put("success", true);
            result.put("data", stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取学生课程关系
     */
    @GetMapping("/page")
    public Map<String, Object> getStudentCoursesByPage(@RequestParam int page, @RequestParam int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", (page - 1) * pageSize);
            params.put("pageSize", pageSize);
            List<StudentCourse> studentCourses = studentCourseService.getStudentCoursesByPage(params);
            int total = studentCourseService.getStudentCoursesCount();
            result.put("success", true);
            result.put("data", studentCourses);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败：" + e.getMessage());
        }
        return result;
    }
}