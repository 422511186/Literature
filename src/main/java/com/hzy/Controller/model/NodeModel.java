package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 12:44
 * @Description:
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "节点模型")
public class NodeModel implements Serializable {
    @ApiModelProperty(value = "节点的id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "节点的名称")
    private String nodeName;
    @ApiModelProperty(value = "节点的路径")
    private String nodePath;
}
