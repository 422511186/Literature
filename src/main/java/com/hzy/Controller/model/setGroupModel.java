package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/14 18:25
 * @Description:
 */

public class setGroupModel implements Serializable {
    @ApiModelProperty("组的名称")
    private String groupName;
    @ApiModelProperty("要邀请用户的用户名")
    private String userName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "setGroupModel{" +
                "groupName='" + groupName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
