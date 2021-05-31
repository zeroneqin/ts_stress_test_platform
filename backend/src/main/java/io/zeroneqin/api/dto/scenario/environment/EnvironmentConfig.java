package io.zeroneqin.api.dto.scenario.environment;

import io.zeroneqin.api.dto.scenario.DatabaseConfig;
import io.zeroneqin.api.dto.scenario.HttpConfig;
import io.zeroneqin.api.dto.scenario.TCPConfig;
import lombok.Data;

import java.util.List;

@Data
public class EnvironmentConfig {
    private CommonConfig commonConfig;
    private HttpConfig httpConfig;
    private List<DatabaseConfig> databaseConfigs;
    private TCPConfig tcpConfig;
}
