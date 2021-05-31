package io.zeroneqin.track.service;

import io.zeroneqin.base.domain.TestCaseIssues;
import io.zeroneqin.base.domain.TestCaseIssuesExample;
import io.zeroneqin.base.mapper.IssuesMapper;
import io.zeroneqin.base.mapper.TestCaseIssuesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestCaseIssueService {

    @Resource
    private TestCaseIssuesMapper testCaseIssuesMapper;
    @Resource
    private IssuesMapper issuesMapper;

    public void delTestCaseIssues(String testCaseId) {
        TestCaseIssuesExample example = new TestCaseIssuesExample();
        example.createCriteria().andTestCaseIdEqualTo(testCaseId);
        List<TestCaseIssues> testCaseIssues = testCaseIssuesMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(testCaseIssues)) {
            List<String> list = testCaseIssues.stream().map(TestCaseIssues::getIssuesId).collect(Collectors.toList());
            list.forEach(id -> {
                issuesMapper.deleteByPrimaryKey(id);
            });
        }
        testCaseIssuesMapper.deleteByExample(example);
    }
}
