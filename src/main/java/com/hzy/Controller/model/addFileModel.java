package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 19:09
 * @Description:
 */

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "从文献库引入文献到其他库下")
public class addFileModel {
    @ApiModelProperty(value = "库的id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "引入后的文献名")
    private String fileName;
    @ApiModelProperty(value = "文献的id")
    private String fileUrl;
}
