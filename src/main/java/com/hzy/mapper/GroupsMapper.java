package com.hzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.entity.Groups;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Auther: hzy
 * @Date: 2021/10/11 20:07
 * @Description:
 */

@Repository
public interface GroupsMapper extends BaseMapper<Groups> {
    String selectGroupsByUsername(@Param("userName") String userName);

    String selectUsernameByGroups(@Param("groupName") String groupName);
}
