package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hzy
 * @Date: 2021/10/13 19:01
 * @Description:
 */
//实体类，记录组长、组名和权限
@TableName(value = "_groups")
@Accessors(chain = true)
public class Groups implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "group_name")
    private String groupName;
    @TableField(value = "owner")
    private String owner;
    @TableField(value = "authority")
    private String Authority;

    public Groups() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getAuthority() {
        return this.Authority;
    }

    public Groups setId(Integer id) {
        this.id = id;
        return this;
    }

    public Groups setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Groups setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Groups setAuthority(String Authority) {
        this.Authority = Authority;
        return this;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Groups)) return false;
        final Groups other = (Groups) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$groupName = this.getGroupName();
        final Object other$groupName = other.getGroupName();
        if (this$groupName == null ? other$groupName != null : !this$groupName.equals(other$groupName)) return false;
        final Object this$owner = this.getOwner();
        final Object other$owner = other.getOwner();
        if (this$owner == null ? other$owner != null : !this$owner.equals(other$owner)) return false;
        final Object this$Authority = this.getAuthority();
        final Object other$Authority = other.getAuthority();
        if (this$Authority == null ? other$Authority != null : !this$Authority.equals(other$Authority)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Groups;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $groupName = this.getGroupName();
        result = result * PRIME + ($groupName == null ? 43 : $groupName.hashCode());
        final Object $owner = this.getOwner();
        result = result * PRIME + ($owner == null ? 43 : $owner.hashCode());
        final Object $Authority = this.getAuthority();
        result = result * PRIME + ($Authority == null ? 43 : $Authority.hashCode());
        return result;
    }

    public String toString() {
        return "Groups(id=" + this.getId() + ", groupName=" + this.getGroupName() + ", owner=" + this.getOwner() + ", Authority=" + this.getAuthority() + ")";
    }
}
