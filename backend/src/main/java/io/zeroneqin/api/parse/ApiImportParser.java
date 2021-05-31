package io.zeroneqin.api.parse;

import io.zeroneqin.api.dto.ApiTestImportRequest;
import io.zeroneqin.api.dto.definition.parse.ApiDefinitionImport;

import java.io.InputStream;

public interface ApiImportParser {
    ApiDefinitionImport parse(InputStream source, ApiTestImportRequest request);
}
