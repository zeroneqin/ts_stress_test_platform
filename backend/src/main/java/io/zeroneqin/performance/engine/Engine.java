package io.zeroneqin.performance.engine;

import io.zeroneqin.base.domain.TestResource;

import java.util.List;

public interface Engine {
    Long getStartTime();

    String getReportId();

    List<TestResource> getResourceList();

    void start();

    void stop();
}
