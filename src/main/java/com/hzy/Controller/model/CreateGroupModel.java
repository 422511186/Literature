package com.hzy.Controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Deprecated
@Data
@ToString
public class CreateGroupModel implements Serializable {
    @ApiModelProperty("节点路径")
    private String relpath;
    @ApiModelProperty("组的名称")
    private String groupName;
}
