package io.zeroneqin.track.issue;

import io.zeroneqin.base.domain.Issues;
import io.zeroneqin.base.domain.TestCaseIssues;
import io.zeroneqin.commons.constants.IssuesManagePlatform;
import io.zeroneqin.commons.user.SessionUser;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.track.request.testcase.IssuesRequest;

import java.util.List;
import java.util.UUID;

public class LocalPlatform extends AbstractIssuePlatform {

    public LocalPlatform(IssuesRequest issuesRequest) {
        super(issuesRequest);
    }

    @Override
    public List<Issues> getIssue() {
        return extIssuesMapper.getIssues(testCaseId, IssuesManagePlatform.Local.toString());
    }

    @Override
    public void addIssue(IssuesRequest issuesRequest) {
        SessionUser user = SessionUtils.getUser();
        String id = UUID.randomUUID().toString();
        Issues issues = new Issues();
        issues.setId(id);
        issues.setStatus("new");
        issues.setReporter(user.getId());
        issues.setTitle(issuesRequest.getTitle());
        issues.setDescription(issuesRequest.getContent());
        issues.setCreateTime(System.currentTimeMillis());
        issues.setUpdateTime(System.currentTimeMillis());
        issues.setPlatform(IssuesManagePlatform.Local.toString());
        issuesMapper.insert(issues);

        TestCaseIssues testCaseIssues = new TestCaseIssues();
        testCaseIssues.setId(UUID.randomUUID().toString());
        testCaseIssues.setIssuesId(id);
        testCaseIssues.setTestCaseId(issuesRequest.getTestCaseId());
        testCaseIssuesMapper.insert(testCaseIssues);
    }

    @Override
    public void deleteIssue(String id) {
        issuesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void testAuth() {

    }

    @Override
    public List<PlatformUser> getPlatformUser() {
        return null;
    }

    @Override
    String getProjectId() {
        return null;
    }

    public void closeIssue(String issueId) {
        Issues issues = new Issues();
        issues.setId(issueId);
        issues.setStatus("closed");
        issuesMapper.updateByPrimaryKeySelective(issues);
    }
}
