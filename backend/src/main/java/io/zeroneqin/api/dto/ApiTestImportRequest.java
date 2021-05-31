package io.zeroneqin.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiTestImportRequest {
    private String name;
    private String moduleId;
    private String modulePath;
    private String environmentId;
    private String projectId;
    private String platform;
    private Boolean useEnvironment;
    // 来自场景的导入不需要存储
    private boolean saved = true;
    private String swaggerUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getUseEnvironment() {
        return useEnvironment;
    }

    public void setUseEnvironment(Boolean useEnvironment) {
        this.useEnvironment = useEnvironment;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getSwaggerUrl() {
        return swaggerUrl;
    }

    public void setSwaggerUrl(String swaggerUrl) {
        this.swaggerUrl = swaggerUrl;
    }
}
