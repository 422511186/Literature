package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 19:09
 * @Description:
 */


@ApiModel(value = "从文献库引入文献到其他库下")
public class addFileModel {

    @ApiModelProperty(value = "库的id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "引入后的文献名")
    private String fileName;
    @ApiModelProperty(value = "文献的id")
    private String fileUrl;

    public addFileModel() {
    }

    public addFileModel(String nodeIdentifier, String fileName, String fileUrl) {
        this.nodeIdentifier = nodeIdentifier;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "addFileModel{" +
                "nodeIdentifier='" + nodeIdentifier + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
