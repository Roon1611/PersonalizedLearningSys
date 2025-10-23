package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.Course;

import java.util.List;

public interface CourseService {
    // 根据ID查询课程
    Course findById(Long id);

    // 查询所有课程
    List<Course> findAll();

    // 根据教师ID查询课程
    List<Course> findByTeacherId(Long teacherId);

    // 根据学生ID查询课程
    List<Course> findByStudentId(Long studentId);

    // 插入课程
    int insert(Course course);

    // 更新课程
    int update(Course course);

    // 删除课程
    int delete(Long id);

    // 学生加入课程
    boolean addStudentToCourse(Long studentId, Long courseId);

    // 学生退出课程
    boolean removeStudentFromCourse(Long studentId, Long courseId);

    // 检查学生是否已加入课程
    boolean checkStudentCourseRelation(Long studentId, Long courseId);

    // 根据关键词搜索课程
    List<Course> searchCourses(String keyword);
}