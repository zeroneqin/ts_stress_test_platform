package io.zeroneqin.api.dto.datacount.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 已执行的案例
 */
@Getter
@Setter
public class ExecutedCaseInfoDTO {
    //排名
    private int sortIndex;

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

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

    //案例名称
    private String caseName;
    //所属测试计划
    private String testPlan;
    //失败次数
    private Long failureTimes;
    //案例类型
    private String caseType;
}
