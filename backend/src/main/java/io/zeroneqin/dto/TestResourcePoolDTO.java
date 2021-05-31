package io.zeroneqin.dto;

import io.zeroneqin.base.domain.TestResource;
import io.zeroneqin.base.domain.TestResourcePool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestResourcePoolDTO extends TestResourcePool {

    private List<TestResource> resources;

    public List<TestResource> getResources() {
        return resources;
    }

    public void setResources(List<TestResource> resources) {
        this.resources = resources;
    }
}
