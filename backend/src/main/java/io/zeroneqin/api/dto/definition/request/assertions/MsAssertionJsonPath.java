package io.zeroneqin.api.dto.definition.request.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class MsAssertionJsonPath extends MsAssertionType {
    private String expect;
    private String expression;
    private String description;

    public MsAssertionJsonPath() {
        setType(MsAssertionType.JSON_PATH);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(expression);
    }
}
