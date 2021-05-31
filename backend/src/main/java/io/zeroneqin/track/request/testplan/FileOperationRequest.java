package io.zeroneqin.track.request.testplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileOperationRequest {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
