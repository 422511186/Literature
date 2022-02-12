package com.hzy.Controller.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@Deprecated
public class CreateGroupModel implements Serializable {

    @ApiModelProperty("节点路径")
    private String relpath;
    @ApiModelProperty("组的名称")
    private String groupName;

    public String getRelpath() {
        return relpath;
    }

    public void setRelpath(String relpath) {
        this.relpath = relpath;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "CreateGroupModel{" +
                "relpath='" + relpath + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
