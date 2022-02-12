package com.hzy.Controller.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hzy
 * @Date: 2022/2/12 18:52
 * @Description:
 */
@Data
@ApiModel(value = "用户信息返回类")
public class userVo {
    @ApiModelProperty(value = "用户账号")
    private String account;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "职业")
    private String profession;
    @ApiModelProperty(value = "邮箱")
    private String mail;
    @ApiModelProperty(value = "电话")
    private String telephone;
    @ApiModelProperty(value = "个人说明")
    private String personalStatement;
}

