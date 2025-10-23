package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.StudentCourse;
import com.personalizedlearning.system.mapper.StudentCourseMapper;
import com.personalizedlearning.system.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生课程关系服务实现类
 */
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public int addStudentCourse(StudentCourse studentCourse) {
        return studentCourseMapper.insert(studentCourse);
    }

    @Override
    public int updateStudentCourse(StudentCourse studentCourse) {
        return studentCourseMapper.update(studentCourse);
    }

    @Override
    public int deleteStudentCourse(Long id) {
        return studentCourseMapper.delete(id);
    }

    @Override
    public StudentCourse getStudentCourseById(Long id) {
        return studentCourseMapper.findById(id);
    }

    @Override
    public List<StudentCourse> getAllStudentCourses() {
        return studentCourseMapper.findAll();
    }

    @Override
    public List<StudentCourse> getCoursesByStudentId(Long studentId) {
        return studentCourseMapper.findCoursesByStudentId(studentId);
    }

    @Override
    public List<StudentCourse> getStudentsByCourseId(Long courseId) {
        return studentCourseMapper.findStudentsByCourseId(courseId);
    }

    @Override
    public StudentCourse getStudentCourseByStudentAndCourse(Long studentId, Long courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("courseId", courseId);
        return studentCourseMapper.findByStudentAndCourse(params);
    }

    @Override
    public Boolean isStudentEnrolled(Long studentId, Long courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("courseId", courseId);
        return studentCourseMapper.isEnrolled(params);
    }

    @Override
    public int countStudentCourses(Long studentId) {
        return studentCourseMapper.countStudentCourses(studentId);
    }

    @Override
    public int countActiveStudentCourses(Long studentId) {
        return studentCourseMapper.countActiveStudentCourses(studentId);
    }

    @Override
    public int countCourseStudents(Long courseId) {
        return studentCourseMapper.countCourseStudents(courseId);
    }

    @Override
    public int countActiveCourseStudents(Long courseId) {
        return studentCourseMapper.countActiveCourseStudents(courseId);
    }

    @Override
    public int getTotalEnrollments() {
        return studentCourseMapper.getTotalEnrollments();
    }

    @Override
    public int getTotalActiveEnrollments() {
        return studentCourseMapper.getTotalActiveEnrollments();
    }

    @Override
    public List<StudentCourse> getStudentCoursesByEnrollDateRange(Map<String, Object> params) {
        return studentCourseMapper.findByEnrollDateRange(params);
    }

    @Override
    public List<StudentCourse> getStudentCoursesByPage(Map<String, Object> params) {
        return studentCourseMapper.findByPage(params);
    }

    @Override
    public int getStudentCoursesCount() {
        return studentCourseMapper.count();
    }
}