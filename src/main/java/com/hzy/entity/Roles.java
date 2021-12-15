package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/11 12:27
 * @Description:
 */
@TableName(value = "_roles")
@Accessors(chain = true)
public class Roles implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String userRoles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userRoles='" + userRoles + '\'' +
                '}';
    }
}
