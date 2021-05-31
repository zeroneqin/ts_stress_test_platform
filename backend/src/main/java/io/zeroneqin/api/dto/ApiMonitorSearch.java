package io.zeroneqin.api.dto;

import lombok.Data;

@Data
public class ApiMonitorSearch {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}