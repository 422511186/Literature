package com.hzy.Controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/25 12:50
 * @Description:
 */

@Data
@ToString
public class KickModel implements Serializable {
    @ApiModelProperty("要移除组的用户名")
    private String userName;
    @ApiModelProperty("组名")
    private String groupName;
}
