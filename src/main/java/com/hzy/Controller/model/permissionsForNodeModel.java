package com.hzy.Controller.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2021/11/4 20:13
 * @Description:
 */
@Data
@ToString
@Accessors(chain = true)
public class permissionsForNodeModel {
    private String nodeIdentifier;
    private String groupName;
}
