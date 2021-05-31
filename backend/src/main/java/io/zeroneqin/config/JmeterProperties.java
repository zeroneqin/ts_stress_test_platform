package io.zeroneqin.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JmeterProperties.JMETER_PREFIX)
@Setter
@Getter
public class JmeterProperties {

    public static final String JMETER_PREFIX = "jmeter";

    private String image;

    private String home;

    private String heap = "-Xms1g -Xmx1g -XX:MaxMetaspaceSize=256m";

    public static String getJmeterPrefix() {
        return JMETER_PREFIX;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }
}
