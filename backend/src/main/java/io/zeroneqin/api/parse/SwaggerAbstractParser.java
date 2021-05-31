package io.zeroneqin.api.parse;

import io.zeroneqin.api.dto.definition.ApiDefinitionResult;
import io.zeroneqin.base.domain.ApiModule;

import java.util.List;

public abstract class SwaggerAbstractParser extends ApiImportAbstractParser {

    protected void buildModule(ApiModule parentModule, ApiDefinitionResult apiDefinition, List<String> tags, boolean isSaved) {
        if (tags != null) {
            tags.forEach(tag -> {
                ApiModule module = buildModule(parentModule, tag, isSaved);
                apiDefinition.setModuleId(module.getId());
            });
        }
    }

}
