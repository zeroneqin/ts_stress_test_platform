package io.zeroneqin.performance.parse;

import io.zeroneqin.performance.engine.EngineContext;

import java.io.InputStream;

public interface EngineSourceParser {
    String parse(EngineContext context, InputStream source) throws Exception;
}
