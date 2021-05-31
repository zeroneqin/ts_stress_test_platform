package io.zeroneqin.api.dto.definition.parse;

import io.zeroneqin.api.dto.definition.ApiDefinitionResult;
import lombok.Data;

import java.util.List;

@Data
public class ApiDefinitionImport {
    private String projectName;
    private String protocol;
    private List<ApiDefinitionResult> data;
}
