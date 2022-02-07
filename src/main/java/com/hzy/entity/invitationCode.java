package com.hzy.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 邀请码实体类
 * @Auther: hzy
 * @Date: 2022/2/8 04:44
 * @Description:
 */
@TableName(value = "invitation_code")
public class invitationCode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "code")
    private String code;
    @TableField(value = "id_occupy")
    private boolean isOccupy;
}
