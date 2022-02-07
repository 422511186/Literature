package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: hzy
 * @Date: 2021/11/7 17:47
 * @Description:
 */


@ApiModel(value = "添加批注的参数对象模型")
public class addAnnotationModel {

    @ApiModelProperty(value = "需要做批注的文献id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "批注")
    private String annotation;

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "addAnnotationModel{" +
                "nodeIdentifier='" + nodeIdentifier + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
