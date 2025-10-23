package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 学生课程关系Mapper接口
 */
@Mapper
public interface StudentCourseMapper {
    int insert(StudentCourse studentCourse);
    int update(StudentCourse studentCourse);
    int delete(Long id);
    StudentCourse findById(Long id);
    List<StudentCourse> findAll();
    List<StudentCourse> findCoursesByStudentId(Long studentId);
    List<StudentCourse> findStudentsByCourseId(Long courseId);
    StudentCourse findByStudentAndCourse(Map<String, Object> params);
    Boolean isEnrolled(Map<String, Object> params);
    int countStudentCourses(Long studentId);
    int countActiveStudentCourses(Long studentId);
    int countCourseStudents(Long courseId);
    int countActiveCourseStudents(Long courseId);
    int getTotalEnrollments();
    int getTotalActiveEnrollments();
    List<StudentCourse> findByEnrollDateRange(Map<String, Object> params);
    List<StudentCourse> findByPage(Map<String, Object> params);
    int count();
}