package io.zeroneqin.api.dto.scenario;

import io.zeroneqin.api.dto.scenario.request.dubbo.ConfigCenter;
import io.zeroneqin.api.dto.scenario.request.dubbo.ConsumerAndService;
import io.zeroneqin.api.dto.scenario.request.dubbo.RegistryCenter;
import lombok.Data;

@Data
public class DubboConfig {
    private ConfigCenter configCenter;
    private RegistryCenter registryCenter;
    private ConsumerAndService consumerAndService;
}
