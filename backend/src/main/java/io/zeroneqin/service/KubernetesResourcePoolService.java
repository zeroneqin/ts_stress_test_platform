package io.zeroneqin.service;

import io.zeroneqin.dto.TestResourcePoolDTO;

public interface KubernetesResourcePoolService {
    boolean validate(TestResourcePoolDTO testResourcePool);
}
