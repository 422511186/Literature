package com.hzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.entity.Roles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Auther: hzy
 * @Date: 2021/10/11 20:08
 * @Description:
 */
@Repository
public interface RolesMapper extends BaseMapper<Roles> {

    String selectRolesByUsername(@Param("username") String username);

    String selectUsernameByRoles(@Param("Roles") String Roles);


}
