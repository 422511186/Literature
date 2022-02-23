package com.hzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.Controller.Vo.userVo;
import com.hzy.entity.userGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2021/10/13 19:14
 * @Description:
 */
@Repository
public interface userGroupMapper extends BaseMapper<userGroup> {

    @Select(value =
           " select username as account,nick_name,unit,profession,mail,telephone,personal_statement "+
           " from user_group "+
            "left join users on user_name=username "+
           " left join user_info on users.id=user_info.id "+
            "where group_name=#{groupName}"
    )
    List<userVo> getGroupUserInfos(@Param(value = "groupName") String groupName);

}
