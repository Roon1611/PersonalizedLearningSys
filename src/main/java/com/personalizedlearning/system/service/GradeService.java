package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.Grade;

import java.util.List;

public interface GradeService {
    // 根据ID查询成绩
    Grade findById(Long id);

    // 根据学生ID查询成绩
    List<Grade> findByStudentId(Long studentId);

    // 根据课程ID查询成绩
    List<Grade> findByCourseId(Long courseId);

    // 根据学生ID和课程ID查询成绩
    Grade findByStudentIdAndCourseId(Long studentId, Long courseId);

    // 查询所有成绩
    List<Grade> findAll();

    // 插入成绩
    int insert(Grade grade);

    // 更新成绩
    int update(Grade grade);

    // 删除成绩
    int delete(Long id);

    // 批量插入或更新成绩
    boolean batchInsertOrUpdate(List<Grade> grades);

    // 根据成绩范围查询
    List<Grade> findByScoreRange(Double minScore, Double maxScore);

    // 计算课程平均分
    Double calculateAverageScoreByCourseId(Long courseId);

    // 计算学生平均分
    Double calculateAverageScoreByStudentId(Long studentId);

    // 获取成绩统计信息（最高分、最低分、平均分等）
    GradeStatistics getGradeStatisticsByCourseId(Long courseId);

    // 内部类，用于存储成绩统计信息
    class GradeStatistics {
        private Double highestScore;
        private Double lowestScore;
        private Double averageScore;
        private Integer totalStudents;
        private Integer passedStudents;
        private Double passRate;

        // 构造函数、getter和setter方法
        public GradeStatistics() {
        }

        public GradeStatistics(Double highestScore, Double lowestScore, Double averageScore, 
                             Integer totalStudents, Integer passedStudents, Double passRate) {
            this.highestScore = highestScore;
            this.lowestScore = lowestScore;
            this.averageScore = averageScore;
            this.totalStudents = totalStudents;
            this.passedStudents = passedStudents;
            this.passRate = passRate;
        }

        public Double getHighestScore() {
            return highestScore;
        }

        public void setHighestScore(Double highestScore) {
            this.highestScore = highestScore;
        }

        public Double getLowestScore() {
            return lowestScore;
        }

        public void setLowestScore(Double lowestScore) {
            this.lowestScore = lowestScore;
        }

        public Double getAverageScore() {
            return averageScore;
        }

        public void setAverageScore(Double averageScore) {
            this.averageScore = averageScore;
        }

        public Integer getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
        }

        public Integer getPassedStudents() {
            return passedStudents;
        }

        public void setPassedStudents(Integer passedStudents) {
            this.passedStudents = passedStudents;
        }

        public Double getPassRate() {
            return passRate;
        }

        public void setPassRate(Double passRate) {
            this.passRate = passRate;
        }
    }
}