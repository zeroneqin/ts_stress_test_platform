package io.zeroneqin.api.dto.definition;

import io.zeroneqin.api.dto.definition.request.MsTestElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaveApiTestCaseRequest {

    private String id;

    private String projectId;

    private String name;

    private String priority;

    private String apiDefinitionId;

    private String description;

    private MsTestElement request;

    private String response;

    private String crateUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private List<String> bodyUploadIds;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getApiDefinitionId() {
        return apiDefinitionId;
    }

    public void setApiDefinitionId(String apiDefinitionId) {
        this.apiDefinitionId = apiDefinitionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MsTestElement getRequest() {
        return request;
    }

    public void setRequest(MsTestElement request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCrateUserId() {
        return crateUserId;
    }

    public void setCrateUserId(String crateUserId) {
        this.crateUserId = crateUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
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

    public List<String> getBodyUploadIds() {
        return bodyUploadIds;
    }

    public void setBodyUploadIds(List<String> bodyUploadIds) {
        this.bodyUploadIds = bodyUploadIds;
    }
}
