package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoadTestWithBLOBs extends LoadTest implements Serializable {
    private String loadConfiguration;

    private String advancedConfiguration;

    private static final long serialVersionUID = 1L;

    public String getLoadConfiguration() {
        return loadConfiguration;
    }

    public void setLoadConfiguration(String loadConfiguration) {
        this.loadConfiguration = loadConfiguration;
    }

    public String getAdvancedConfiguration() {
        return advancedConfiguration;
    }

    public void setAdvancedConfiguration(String advancedConfiguration) {
        this.advancedConfiguration = advancedConfiguration;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}