package com.personalizedlearning.system.mapper;

import com.personalizedlearning.system.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 根据ID查询课程
    Course findById(@Param("id") Long id);

    // 查询所有课程
    List<Course> findAll();

    // 根据教师ID查询课程
    List<Course> findByTeacherId(@Param("teacherId") Long teacherId);

    // 根据学生ID查询课程
    List<Course> findByStudentId(@Param("studentId") Long studentId);

    // 插入课程
    int insert(Course course);

    // 更新课程
    int update(Course course);

    // 删除课程
    int delete(@Param("id") Long id);

    // 学生加入课程
    int addStudentToCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 学生退出课程
    int removeStudentFromCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 检查学生是否已加入课程
    int checkStudentCourseRelation(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}