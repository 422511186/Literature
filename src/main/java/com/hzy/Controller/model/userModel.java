package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/9/26 10:46
 * @Description:
 */
@ToString
@Data
@ApiModel(value = "登录的参数模型")
public class userModel implements Serializable {
    @ApiModelProperty("注册用户的用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
