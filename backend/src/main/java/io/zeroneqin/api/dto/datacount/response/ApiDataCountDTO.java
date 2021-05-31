package io.zeroneqin.api.dto.datacount.response;

import io.zeroneqin.api.dto.datacount.ApiDataCountResult;
import io.zeroneqin.api.dto.scenario.request.RequestType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接口数据统计返回
 */
@Getter
@Setter
public class ApiDataCountDTO {

    /**
     * 接口统计
     */
    private long allApiDataCountNumber = 0;
    /**
     * http接口统计
     */
    private long httpApiDataCountNumber = 0;
    /**
     * rpc接口统计
     */
    private long rpcApiDataCountNumber = 0;
    /**
     * tcp接口统计
     */
    private long tcpApiDataCountNumber = 0;
    /**
     * sql接口统计
     */
    private long sqlApiDataCountNumber = 0;

    private String httpCountStr = "";
    private String rpcCountStr = "";
    private String tcpCountStr = "";
    private String sqlCountStr = "";

    /**
     * 本周新增数量
     */
    private long thisWeekAddedCount = 0;
    /**
     * 本周执行数量
     */
    private long thisWeekExecutedCount = 0;
    /**
     * 历史总执行数量
     */
    private long executedCount = 0;

    /**
     * 进行中
     */
    private long runningCount = 0;
    /**
     * 未开始
     */
    private long notStartedCount = 0;
    /**
     * 已完成
     */
    private long finishedCount = 0;
    /**
     * 未覆盖
     */
    private long uncoverageCount = 0;
    /**
     * 已覆盖
     */
    private long coverageCount = 0;
    /**
     * 未执行
     */
    private long unexecuteCount = 0;
    /**
     * 执行失败
     */
    private long executionFailedCount = 0;
    /**
     * 执行通过
     */
    private long executionPassCount = 0;
    /**
     * 失败
     */
    private long failedCount = 0;
    /**
     * 成功
     */
    private long successCount = 0;

    /**
     * 完成率
     */
    private String completionRage = " 0%";
    /**
     * 覆盖率
     */
    private String coverageRage = " 0%";
    /**
     * 通过率
     */
    private String passRage = " 0%";
    /**
     * 成功率
     */
    private String successRage = " 0%";

    public ApiDataCountDTO(){}

    /**
     * 对Protocal视角对查询结果进行统计
     * @param countResultList 查询参数
     */
    public void countProtocal(List<ApiDataCountResult> countResultList){
        for (ApiDataCountResult countResult :
                countResultList) {
            switch (countResult.getGroupField().toUpperCase()){
                case RequestType.DUBBO:
                    this.rpcApiDataCountNumber += countResult.getCountNumber();
                    break;
                case RequestType.HTTP:
                    this.httpApiDataCountNumber += countResult.getCountNumber();
                    break;
                case RequestType.SQL:
                    this.sqlApiDataCountNumber += countResult.getCountNumber();
                    break;
                case RequestType.TCP:
                    this.tcpApiDataCountNumber += countResult.getCountNumber();
                    break;
                default:
                    break;
            }
            allApiDataCountNumber += countResult.getCountNumber();
        }
    }


    /**
     * 对Status视角对查询结果进行统计
     * @param countResultList 查询参数
     */
    public void countStatus(List<ApiDataCountResult> countResultList){
        for (ApiDataCountResult countResult :
                countResultList) {
            if("Underway".equals(countResult.getGroupField())){
                //运行中
                this.runningCount+= countResult.getCountNumber();
            }else if("Completed".equals(countResult.getGroupField())){
                //已完成
                this.finishedCount+= countResult.getCountNumber();
            }else if("Prepare".equals(countResult.getGroupField())){
                this.notStartedCount+= countResult.getCountNumber();
            }
        }
    }

    public void countApiCoverage(List<ApiDataCountResult> countResultList) {

        for (ApiDataCountResult countResult : countResultList) {
            if("coverage".equals(countResult.getGroupField())){
                this.coverageCount+= countResult.getCountNumber();
            }else if("uncoverage".equals(countResult.getGroupField())){
                this.uncoverageCount+= countResult.getCountNumber();
            }
        }
    }

    public void countRunResult(List<ApiDataCountResult> countResultByRunResult) {

        for (ApiDataCountResult countResult : countResultByRunResult) {
            if("notRun".equals(countResult.getGroupField())){
                this.unexecuteCount+= countResult.getCountNumber();
            }else if("Fail".equals(countResult.getGroupField())){
                this.executionFailedCount+= countResult.getCountNumber();
            }else {
                this.executionPassCount+= countResult.getCountNumber();
            }
        }
    }

    public void countScheduleExecute(List<ApiDataCountResult> allExecuteResult) {
        for (ApiDataCountResult countResult : allExecuteResult) {
            if("Success".equals(countResult.getGroupField())){
                this.successCount+= countResult.getCountNumber();
                this.executedCount+= countResult.getCountNumber();
            }else if("Error".equals(countResult.getGroupField())||"Fail".equals(countResult.getGroupField())){
                this.failedCount+= countResult.getCountNumber();
                this.executedCount+= countResult.getCountNumber();
            }
        }
    }

    public long getAllApiDataCountNumber() {
        return allApiDataCountNumber;
    }

    public void setAllApiDataCountNumber(long allApiDataCountNumber) {
        this.allApiDataCountNumber = allApiDataCountNumber;
    }

    public long getHttpApiDataCountNumber() {
        return httpApiDataCountNumber;
    }

    public void setHttpApiDataCountNumber(long httpApiDataCountNumber) {
        this.httpApiDataCountNumber = httpApiDataCountNumber;
    }

    public long getRpcApiDataCountNumber() {
        return rpcApiDataCountNumber;
    }

    public void setRpcApiDataCountNumber(long rpcApiDataCountNumber) {
        this.rpcApiDataCountNumber = rpcApiDataCountNumber;
    }

    public long getTcpApiDataCountNumber() {
        return tcpApiDataCountNumber;
    }

    public void setTcpApiDataCountNumber(long tcpApiDataCountNumber) {
        this.tcpApiDataCountNumber = tcpApiDataCountNumber;
    }

    public long getSqlApiDataCountNumber() {
        return sqlApiDataCountNumber;
    }

    public void setSqlApiDataCountNumber(long sqlApiDataCountNumber) {
        this.sqlApiDataCountNumber = sqlApiDataCountNumber;
    }

    public String getHttpCountStr() {
        return httpCountStr;
    }

    public void setHttpCountStr(String httpCountStr) {
        this.httpCountStr = httpCountStr;
    }

    public String getRpcCountStr() {
        return rpcCountStr;
    }

    public void setRpcCountStr(String rpcCountStr) {
        this.rpcCountStr = rpcCountStr;
    }

    public String getTcpCountStr() {
        return tcpCountStr;
    }

    public void setTcpCountStr(String tcpCountStr) {
        this.tcpCountStr = tcpCountStr;
    }

    public String getSqlCountStr() {
        return sqlCountStr;
    }

    public void setSqlCountStr(String sqlCountStr) {
        this.sqlCountStr = sqlCountStr;
    }

    public long getThisWeekAddedCount() {
        return thisWeekAddedCount;
    }

    public void setThisWeekAddedCount(long thisWeekAddedCount) {
        this.thisWeekAddedCount = thisWeekAddedCount;
    }

    public long getThisWeekExecutedCount() {
        return thisWeekExecutedCount;
    }

    public void setThisWeekExecutedCount(long thisWeekExecutedCount) {
        this.thisWeekExecutedCount = thisWeekExecutedCount;
    }

    public long getExecutedCount() {
        return executedCount;
    }

    public void setExecutedCount(long executedCount) {
        this.executedCount = executedCount;
    }

    public long getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(long runningCount) {
        this.runningCount = runningCount;
    }

    public long getNotStartedCount() {
        return notStartedCount;
    }

    public void setNotStartedCount(long notStartedCount) {
        this.notStartedCount = notStartedCount;
    }

    public long getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(long finishedCount) {
        this.finishedCount = finishedCount;
    }

    public long getUncoverageCount() {
        return uncoverageCount;
    }

    public void setUncoverageCount(long uncoverageCount) {
        this.uncoverageCount = uncoverageCount;
    }

    public long getCoverageCount() {
        return coverageCount;
    }

    public void setCoverageCount(long coverageCount) {
        this.coverageCount = coverageCount;
    }

    public long getUnexecuteCount() {
        return unexecuteCount;
    }

    public void setUnexecuteCount(long unexecuteCount) {
        this.unexecuteCount = unexecuteCount;
    }

    public long getExecutionFailedCount() {
        return executionFailedCount;
    }

    public void setExecutionFailedCount(long executionFailedCount) {
        this.executionFailedCount = executionFailedCount;
    }

    public long getExecutionPassCount() {
        return executionPassCount;
    }

    public void setExecutionPassCount(long executionPassCount) {
        this.executionPassCount = executionPassCount;
    }

    public long getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(long failedCount) {
        this.failedCount = failedCount;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public String getCompletionRage() {
        return completionRage;
    }

    public void setCompletionRage(String completionRage) {
        this.completionRage = completionRage;
    }

    public String getCoverageRage() {
        return coverageRage;
    }

    public void setCoverageRage(String coverageRage) {
        this.coverageRage = coverageRage;
    }

    public String getPassRage() {
        return passRage;
    }

    public void setPassRage(String passRage) {
        this.passRage = passRage;
    }

    public String getSuccessRage() {
        return successRage;
    }

    public void setSuccessRage(String successRage) {
        this.successRage = successRage;
    }
}
