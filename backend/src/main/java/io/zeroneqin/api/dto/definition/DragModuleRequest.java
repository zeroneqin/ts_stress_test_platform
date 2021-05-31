package io.zeroneqin.api.dto.definition;

import io.zeroneqin.base.domain.ApiModule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DragModuleRequest extends ApiModule {

    List<String> nodeIds;
    ApiModuleDTO nodeTree;

    public List<String> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<String> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public ApiModuleDTO getNodeTree() {
        return nodeTree;
    }

    public void setNodeTree(ApiModuleDTO nodeTree) {
        this.nodeTree = nodeTree;
    }
}
