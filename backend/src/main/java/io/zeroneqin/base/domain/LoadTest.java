package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoadTest implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private String description;

    private Long createTime;

    private Long updateTime;

    private String status;

    private String testResourcePoolId;

    private Integer testResourcePoolSlaveNum;

    private String userId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestResourcePoolId() {
        return testResourcePoolId;
    }

    public void setTestResourcePoolId(String testResourcePoolId) {
        this.testResourcePoolId = testResourcePoolId;
    }


    public Integer getTestResourcePoolSlaveNum() {
        return testResourcePoolSlaveNum;
    }

    public void setTestResourcePoolSlaveNum(Integer testResourcePoolSlaveNum) {
        this.testResourcePoolSlaveNum = testResourcePoolSlaveNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}