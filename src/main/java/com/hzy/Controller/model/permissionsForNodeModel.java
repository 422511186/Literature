package com.hzy.Controller.model;

import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2021/11/4 20:13
 * @Description:
 */


public class permissionsForNodeModel {
    private String nodeIdentifier;
    private String groupName;

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "permissionsForNodeModel{" +
                "nodeIdentifier='" + nodeIdentifier + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
