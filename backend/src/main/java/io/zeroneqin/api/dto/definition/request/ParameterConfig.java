package io.zeroneqin.api.dto.definition.request;

import io.zeroneqin.api.dto.scenario.KeyValue;
import io.zeroneqin.api.dto.scenario.environment.EnvironmentConfig;
import lombok.Data;

import java.util.List;

@Data
public class ParameterConfig {
    // 环境配置
    private EnvironmentConfig config;
    // 公共场景参数
    private List<KeyValue> variables;
    // 公共Cookie
    private boolean enableCookieShare;

    public EnvironmentConfig getConfig() {
        return config;
    }

    public void setConfig(EnvironmentConfig config) {
        this.config = config;
    }

    public List<KeyValue> getVariables() {
        return variables;
    }

    public void setVariables(List<KeyValue> variables) {
        this.variables = variables;
    }

    public boolean isEnableCookieShare() {
        return enableCookieShare;
    }

    public void setEnableCookieShare(boolean enableCookieShare) {
        this.enableCookieShare = enableCookieShare;
    }
}
