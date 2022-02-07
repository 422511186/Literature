package com.hzy.Controller.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/25 12:50
 * @Description:
 */


public class KickModel implements Serializable {
    @ApiModelProperty("要移除组的用户名")
    private String userName;
    @ApiModelProperty("组名")
    private String groupName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "KickModel{" +
                "userName='" + userName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
