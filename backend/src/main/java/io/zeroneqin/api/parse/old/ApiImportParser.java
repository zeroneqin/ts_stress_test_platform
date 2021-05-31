package io.zeroneqin.api.parse.old;

import io.zeroneqin.api.dto.ApiTestImportRequest;
import io.zeroneqin.api.dto.definition.parse.ApiDefinitionImport;
import io.zeroneqin.api.dto.parse.ApiImport;

import java.io.InputStream;

public interface ApiImportParser {
    ApiImport parse(InputStream source, ApiTestImportRequest request);
    ApiDefinitionImport parseApi(InputStream source, ApiTestImportRequest request);

}
