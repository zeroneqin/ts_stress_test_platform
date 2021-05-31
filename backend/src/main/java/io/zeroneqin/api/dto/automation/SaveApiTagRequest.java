package io.zeroneqin.api.dto.automation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveApiTagRequest {
    private String id;

    private String projectId;

    private String userId;

    private String name;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
