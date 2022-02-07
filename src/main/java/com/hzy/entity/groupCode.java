package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 团队与邀请码关联实体
 * @Auther: hzy
 * @Date: 2022/2/8 04:44
 * @Description:
 */
@TableName(value = "group_code")
public class groupCode {
    @TableField(value = "code_id")
    private Integer codeId;
    @TableField(value = "group_id")
    private Integer groupId;

}
