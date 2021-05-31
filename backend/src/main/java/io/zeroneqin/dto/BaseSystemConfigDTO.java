package io.zeroneqin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSystemConfigDTO {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
