package io.zeroneqin.service;

import io.zeroneqin.track.request.testplan.TestPlanRequest;

import java.util.Set;

public interface QuotaService {

    void checkAPITestQuota();

    void checkLoadTestQuota(TestPlanRequest request, boolean checkPerformance);

    Set<String> getQuotaResourcePools();
}
