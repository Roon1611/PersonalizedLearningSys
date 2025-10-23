package com.personalizedlearning.system.service.impl;

import com.personalizedlearning.system.entity.AssignmentSubmission;
import com.personalizedlearning.system.mapper.AssignmentSubmissionMapper;
import com.personalizedlearning.system.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionMapper submissionMapper;

    @Override
    public AssignmentSubmission findById(Long id) {
        return submissionMapper.findById(id);
    }

    @Override
    public List<AssignmentSubmission> findByAssignmentId(Long assignmentId) {
        return submissionMapper.findByAssignmentId(assignmentId);
    }

    @Override
    public AssignmentSubmission findByStudentIdAndAssignmentId(Long studentId, Long assignmentId) {
        return submissionMapper.findByStudentIdAndAssignmentId(studentId, assignmentId);
    }

    @Override
    public List<AssignmentSubmission> findByStudentId(Long studentId) {
        return submissionMapper.findByStudentId(studentId);
    }

    @Override
    public List<AssignmentSubmission> findAll() {
        return submissionMapper.findAll();
    }

    @Override
    @Transactional
    public int insert(AssignmentSubmission submission) {
        submission.setCreatedAt(new Date());
        submission.setUpdatedAt(new Date());
        if (submission.getStatus() == null) {
            submission.setStatus("PENDING");
        }
        return submissionMapper.insert(submission);
    }

    @Override
    @Transactional
    public int update(AssignmentSubmission submission) {
        submission.setUpdatedAt(new Date());
        return submissionMapper.update(submission);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return submissionMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateStatusAndScore(Long id, String status, Double score, String feedback) {
        return submissionMapper.updateStatusAndScore(id, status, score, feedback);
    }

    @Override
    public List<AssignmentSubmission> findByStatus(String status) {
        return submissionMapper.findByStatus(status);
    }

    @Override
    @Transactional
    public boolean submitAssignment(Long studentId, Long assignmentId, String fileUrl, String content) {
        // 检查是否已经提交过
        AssignmentSubmission existingSubmission = submissionMapper.findByStudentIdAndAssignmentId(studentId, assignmentId);
        if (existingSubmission != null) {
            // 如果已经提交过，更新提交
            existingSubmission.setFileUrl(fileUrl);
            existingSubmission.setContent(content);
            existingSubmission.setUpdatedAt(new Date());
            existingSubmission.setStatus("SUBMITTED");
            int result = submissionMapper.update(existingSubmission);
            return result > 0;
        } else {
            // 如果没有提交过，创建新提交
            AssignmentSubmission submission = new AssignmentSubmission();
            submission.setStudentId(studentId);
            submission.setAssignmentId(assignmentId);
            submission.setFileUrl(fileUrl);
            submission.setContent(content);
            submission.setStatus("SUBMITTED");
            submission.setCreatedAt(new Date());
            submission.setUpdatedAt(new Date());
            int result = submissionMapper.insert(submission);
            return result > 0;
        }
    }

    @Override
    @Transactional
    public boolean gradeAssignment(Long submissionId, Double score, String feedback) {
        AssignmentSubmission submission = submissionMapper.findById(submissionId);
        if (submission != null) {
            int result = submissionMapper.updateStatusAndScore(submissionId, "GRADED", score, feedback);
            return result > 0;
        }
        return false;
    }

    @Override
    public List<AssignmentSubmission> findUngradedSubmissions() {
        return submissionMapper.findByStatus("SUBMITTED");
    }
}