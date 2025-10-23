package com.personalizedlearning.system.service;

import com.personalizedlearning.system.entity.LearningProgress;

import java.util.List;

public interface LearningProgressService {
    // 根据ID查询学习进度
    LearningProgress findById(Long id);

    // 根据学生ID和课程ID查询学习进度
    LearningProgress findByStudentIdAndCourseId(Long studentId, Long courseId);

    // 根据学生ID查询所有课程的学习进度
    List<LearningProgress> findByStudentId(Long studentId);

    // 根据课程ID查询所有学生的学习进度
    List<LearningProgress> findByCourseId(Long courseId);

    // 查询所有学习进度
    List<LearningProgress> findAll();

    // 插入学习进度
    int insert(LearningProgress progress);

    // 更新学习进度
    int update(LearningProgress progress);

    // 删除学习进度
    int delete(Long id);

    // 批量更新学习进度
    boolean batchUpdate(List<LearningProgress> progresses);

    // 根据完成度查询学习进度
    List<LearningProgress> findByCompletionRate(Integer minRate, Integer maxRate);

    // 更新学生课程完成度
    boolean updateCompletionRate(Long studentId, Long courseId, Integer completionRate);

    // 计算课程平均完成度
    Double calculateAverageCompletionRateByCourseId(Long courseId);

    // 计算学生平均完成度
    Double calculateAverageCompletionRateByStudentId(Long studentId);

    // 获取学习进度统计信息
    ProgressStatistics getProgressStatisticsByCourseId(Long courseId);

    // 获取模拟学习进度数据（用于在没有数据库连接时展示）
    List<LearningProgress> getMockProgressData();

    // 内部类，用于存储学习进度统计信息
    class ProgressStatistics {
        private Double averageCompletionRate;
        private Integer highestCompletionRate;
        private Integer lowestCompletionRate;
        private Integer totalStudents;
        private Integer completedStudents;
        private Double completionRate;

        // 构造函数、getter和setter方法
        public ProgressStatistics() {
        }

        public ProgressStatistics(Double averageCompletionRate, Integer highestCompletionRate, 
                                 Integer lowestCompletionRate, Integer totalStudents, 
                                 Integer completedStudents, Double completionRate) {
            this.averageCompletionRate = averageCompletionRate;
            this.highestCompletionRate = highestCompletionRate;
            this.lowestCompletionRate = lowestCompletionRate;
            this.totalStudents = totalStudents;
            this.completedStudents = completedStudents;
            this.completionRate = completionRate;
        }

        public Double getAverageCompletionRate() {
            return averageCompletionRate;
        }

        public void setAverageCompletionRate(Double averageCompletionRate) {
            this.averageCompletionRate = averageCompletionRate;
        }

        public Integer getHighestCompletionRate() {
            return highestCompletionRate;
        }

        public void setHighestCompletionRate(Integer highestCompletionRate) {
            this.highestCompletionRate = highestCompletionRate;
        }

        public Integer getLowestCompletionRate() {
            return lowestCompletionRate;
        }

        public void setLowestCompletionRate(Integer lowestCompletionRate) {
            this.lowestCompletionRate = lowestCompletionRate;
        }

        public Integer getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
        }

        public Integer getCompletedStudents() {
            return completedStudents;
        }

        public void setCompletedStudents(Integer completedStudents) {
            this.completedStudents = completedStudents;
        }

        public Double getCompletionRate() {
            return completionRate;
        }

        public void setCompletionRate(Double completionRate) {
            this.completionRate = completionRate;
        }
    }
}