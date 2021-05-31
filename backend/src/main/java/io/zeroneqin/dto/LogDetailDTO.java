package io.zeroneqin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDetailDTO {
    private String resourceId;
    private String resourceName;
    private String content;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
