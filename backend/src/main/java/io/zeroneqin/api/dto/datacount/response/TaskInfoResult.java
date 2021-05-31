package io.zeroneqin.api.dto.datacount.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务信息 返回DTO
 */
@Getter
@Setter
public class TaskInfoResult {
    //序号
    private int index;
    //任务ID
    private String taskID;
    //场景名称
    private String scenario;
    //规则
    private String rule;
    //任务状态
    private boolean taskStatus;
    //下次执行时间
    private Long nextExecutionTime;
    //创建人
    private String creator;
    //更新时间
    private Long updateTime;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(Long nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
