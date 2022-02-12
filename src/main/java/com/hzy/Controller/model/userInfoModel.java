package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2022/2/12 18:17
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户更多信息接收参数")
public class userInfoModel {

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
