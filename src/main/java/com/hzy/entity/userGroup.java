package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/13 18:56
 * @Description:
 */
@TableName(value = "user_group")
@Accessors(chain = true)
public class userGroup implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "group_name")
    private String groupName;

    public userGroup() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public userGroup setId(Integer id) {
        this.id = id;
        return this;
    }

    public userGroup setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public userGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof userGroup)) return false;
        final userGroup other = (userGroup) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$groupName = this.getGroupName();
        final Object other$groupName = other.getGroupName();
        if (this$groupName == null ? other$groupName != null : !this$groupName.equals(other$groupName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof userGroup;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $groupName = this.getGroupName();
        result = result * PRIME + ($groupName == null ? 43 : $groupName.hashCode());
        return result;
    }

    public String toString() {
        return "userGroup(id=" + this.getId() + ", userName=" + this.getUserName() + ", groupName=" + this.getGroupName() + ")";
    }
}
