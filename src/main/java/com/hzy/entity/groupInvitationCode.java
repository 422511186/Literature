package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2022/2/14 20:40
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "group_invitation_code")
@Accessors(chain = true)
public class groupInvitationCode {
    @TableId(value = "group_name")
    private String groupName;
    private String code;
}
