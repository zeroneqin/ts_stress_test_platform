package io.zeroneqin.api.dto.definition.request.controller;

import com.alibaba.fastjson.annotation.JSONType;
import io.zeroneqin.api.dto.definition.request.MsTestElement;
import io.zeroneqin.api.dto.definition.request.ParameterConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.control.IfController;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "IfController")
public class MsIfController extends MsTestElement {
    private String type = "IfController";
    private String id;
    private String variable;
    private String operator;
    private String value;

    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, ParameterConfig config) {
        if (!this.isEnable()) {
            return;
        }
        final HashTree groupTree = tree.add(ifController());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(groupTree, el.getHashTree(), config);
            });
        }
    }

    private IfController ifController() {
        IfController ifController = new IfController();
        ifController.setEnabled(true);
        ifController.setName(this.getLabel());
        ifController.setCondition(this.getCondition());
        ifController.setProperty(TestElement.TEST_CLASS, IfController.class.getName());
        ifController.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("IfControllerPanel"));
        ifController.setEvaluateAll(false);
        ifController.setUseExpression(true);
        return ifController;
    }

    public boolean isValid() {
        if (StringUtils.contains(operator, "empty")) {
            return StringUtils.isNotBlank(variable);
        }
        return StringUtils.isNotBlank(variable) && StringUtils.isNotBlank(operator) && StringUtils.isNotBlank(value);
    }

    public String getLabel() {
        if (isValid()) {
            String label = variable + " " + operator;
            if (StringUtils.isNotBlank(value)) {
                label += " " + this.value;
            }
            return label;
        }
        return "";
    }

    public String getCondition() {
        String variable = "\"" + this.variable + "\"";
        String operator = this.operator;
        String value = "\"" + this.value + "\"";

        if (StringUtils.contains(operator, "~")) {
            value = "\".*" + this.value + ".*\"";
        }

        if (StringUtils.equals(operator, "is empty")) {
            variable = "empty(" + variable + ")";
            operator = "";
            value = "";
        }

        if (StringUtils.equals(operator, "is not empty")) {
            variable = "!empty(" + variable + ")";
            operator = "";
            value = "";
        }

        return "${__jexl3(" + variable + operator + value + ")}";
    }

}
