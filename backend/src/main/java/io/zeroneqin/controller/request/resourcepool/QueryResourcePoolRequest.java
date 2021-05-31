package io.zeroneqin.controller.request.resourcepool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResourcePoolRequest {
    private String name;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
