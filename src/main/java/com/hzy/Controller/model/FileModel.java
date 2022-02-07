package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 17:59
 * @Description:
 */

@ApiModel(value = "返回文献索引的信息")
public class FileModel implements Serializable {

    @ApiModelProperty(value = "文献名(自己取的)")
    private String nodeName;
    @ApiModelProperty(value = "文献对应文献库中的id")
    private String nodeIdentifier;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeIdentifier='" + nodeIdentifier + '\'' +
                '}';
    }
}
