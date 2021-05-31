package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class ServiceIntegration implements Serializable {
    private String id;

    private String organizationId;

    private String platform;

    private String configuration;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}