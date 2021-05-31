package io.zeroneqin.performance.parse;

import io.zeroneqin.commons.constants.FileType;
import io.zeroneqin.performance.parse.xml.XmlEngineSourceParse;

public class EngineSourceParserFactory {
    public static EngineSourceParser createEngineSourceParser(String type) {
        final FileType engineType = FileType.valueOf(type);

        if (FileType.JMX.equals(engineType)) {
            return new XmlEngineSourceParse();
        }

        return null;
    }
}
