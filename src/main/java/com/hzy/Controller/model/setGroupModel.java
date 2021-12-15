package com.hzy.Controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/14 18:25
 * @Description:
 */
@Data
@ToString
public class setGroupModel implements Serializable {
    @ApiModelProperty("组的名称")
    private String groupName;
    @ApiModelProperty("要邀请用户的用户名")
    private String userName;
}
