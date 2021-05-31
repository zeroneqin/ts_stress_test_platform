package io.zeroneqin.track.service;

import io.zeroneqin.base.domain.TestCaseReport;
import io.zeroneqin.base.domain.TestCaseReportExample;
import io.zeroneqin.base.domain.TestCaseReportTemplate;
import io.zeroneqin.base.domain.TestPlan;
import io.zeroneqin.base.mapper.TestCaseReportMapper;
import io.zeroneqin.base.mapper.TestCaseReportTemplateMapper;
import io.zeroneqin.base.mapper.TestPlanMapper;
import io.zeroneqin.base.mapper.ext.ExtTestPlanMapper;
import io.zeroneqin.commons.utils.BeanUtils;
import io.zeroneqin.track.request.testCaseReport.CreateReportRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestCaseReportService {

    @Resource
    TestCaseReportMapper testCaseReportMapper;

    @Resource
    TestPlanMapper testPlanMapper;

    @Resource
    ExtTestPlanMapper extTestPlanMapper;

    @Resource
    TestCaseReportTemplateMapper testCaseReportTemplateMapper;

    public List<TestCaseReport> listTestCaseReport(TestCaseReport request) {
        TestCaseReportExample example = new TestCaseReportExample();
        if (StringUtils.isNotBlank(request.getName())) {
            example.createCriteria().andNameEqualTo(request.getName());
        }
        return testCaseReportMapper.selectByExample(example);
    }

    public TestCaseReport getTestCaseReport(String id) {
        return testCaseReportMapper.selectByPrimaryKey(id);
    }

    public void addTestCaseReport(TestCaseReport testCaseReport) {
        testCaseReport.setId(UUID.randomUUID().toString());
        testCaseReportMapper.insert(testCaseReport);
    }

    public void editTestCaseReport(TestCaseReport TestCaseReport) {
        testCaseReportMapper.updateByPrimaryKeyWithBLOBs(TestCaseReport);
    }

    public int deleteTestCaseReport(String id) {
        return testCaseReportMapper.deleteByPrimaryKey(id);
    }

    public String addTestCaseReportByTemplateId(CreateReportRequest request) {
        TestCaseReportTemplate template = testCaseReportTemplateMapper.selectByPrimaryKey(request.getTemplateId());
        TestCaseReport report = new TestCaseReport();
        BeanUtils.copyBean(report, template);
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(request.getPlanId());
        report.setName(testPlan.getName());
        report.setId(UUID.randomUUID().toString());
        testCaseReportMapper.insert(report);
        testPlan.setReportId(report.getId());
        testPlanMapper.updateByPrimaryKeySelective(testPlan);
        return report.getId();
    }
}
