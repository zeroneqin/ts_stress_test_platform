package io.zeroneqin.api.dto.automation;

import io.zeroneqin.base.domain.ApiScenarioModule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DragApiScenarioModuleRequest extends ApiScenarioModule {

    List<String> nodeIds;
    ApiScenarioModuleDTO nodeTree;

    public List<String> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<String> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public ApiScenarioModuleDTO getNodeTree() {
        return nodeTree;
    }

    public void setNodeTree(ApiScenarioModuleDTO nodeTree) {
        this.nodeTree = nodeTree;
    }
}
