package io.zeroneqin.api.dto.datacount;

import lombok.Getter;
import lombok.Setter;

/**
 * 已执行的案例
 */
@Getter
@Setter
public class ExecutedCaseInfoResult {
    //案例名称
    private String caseName;
    //所属测试计划
    private String testPlan;
    //失败次数
    private Long failureTimes;
    //案例类型
    private String caseType;

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(String testPlan) {
        this.testPlan = testPlan;
    }

    public Long getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(Long failureTimes) {
        this.failureTimes = failureTimes;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
}
