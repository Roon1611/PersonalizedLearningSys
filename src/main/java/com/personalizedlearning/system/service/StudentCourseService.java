package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.StudentCourse;
import java.util.List;
import java.util.Map;

/**
 * 学生课程关系服务接口
 */
public interface StudentCourseService {
    int addStudentCourse(StudentCourse studentCourse);
    int updateStudentCourse(StudentCourse studentCourse);
    int deleteStudentCourse(Long id);
    StudentCourse getStudentCourseById(Long id);
    List<StudentCourse> getAllStudentCourses();
    List<StudentCourse> getCoursesByStudentId(Long studentId);
    List<StudentCourse> getStudentsByCourseId(Long courseId);
    StudentCourse getStudentCourseByStudentAndCourse(Long studentId, Long courseId);
    Boolean isStudentEnrolled(Long studentId, Long courseId);
    int countStudentCourses(Long studentId);
    int countActiveStudentCourses(Long studentId);
    int countCourseStudents(Long courseId);
    int countActiveCourseStudents(Long courseId);
    int getTotalEnrollments();
    int getTotalActiveEnrollments();
    List<StudentCourse> getStudentCoursesByEnrollDateRange(Map<String, Object> params);
    List<StudentCourse> getStudentCoursesByPage(Map<String, Object> params);
    int getStudentCoursesCount();
}