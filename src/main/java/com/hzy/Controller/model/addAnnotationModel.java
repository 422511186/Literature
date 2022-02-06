package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2021/11/7 17:47
 * @Description:
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "添加批注的参数对象模型")
public class addAnnotationModel {

    @ApiModelProperty(value = "需要做批注的文献id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "批注")
    private String annotation;

}
