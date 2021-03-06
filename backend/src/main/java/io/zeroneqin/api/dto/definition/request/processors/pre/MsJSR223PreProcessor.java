package io.zeroneqin.api.dto.definition.request.processors.pre;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import io.zeroneqin.api.dto.definition.request.MsTestElement;
import io.zeroneqin.api.dto.definition.request.ParameterConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.modifiers.JSR223PreProcessor;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "JSR223PreProcessor")
public class MsJSR223PreProcessor extends MsTestElement {
    private String type = "JSR223PreProcessor";

    @JSONField(ordinal = 20)
    private String script;

    @JSONField(ordinal = 21)
    private String scriptLanguage;

    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, ParameterConfig config) {
        if (!this.isEnable()) {
            return;
        }
        JSR223PreProcessor processor = new JSR223PreProcessor();
        processor.setEnabled(true);
        if (StringUtils.isNotEmpty(this.getName())) {
            processor.setName(this.getName());
        } else {
            processor.setName("JSR223PreProcessor");
        }
        processor.setProperty(TestElement.TEST_CLASS, JSR223PreProcessor.class.getName());
        processor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
        processor.setProperty("cacheKey", "true");
        processor.setProperty("scriptLanguage", this.getScriptLanguage());
        processor.setProperty("script", this.getScript());

        final HashTree jsr223PreTree = tree.add(processor);
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(jsr223PreTree, el.getHashTree(), config);
            });
        }
    }

}
