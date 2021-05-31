package io.zeroneqin.api.dto.definition;

import io.zeroneqin.track.dto.TreeNodeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiModuleDTO extends TreeNodeDTO<ApiModuleDTO> {
    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
