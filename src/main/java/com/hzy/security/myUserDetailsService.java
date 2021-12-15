package com.hzy.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzy.entity.Users;
import com.hzy.mapper.GroupsMapper;
import com.hzy.mapper.RolesMapper;
import com.hzy.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2021/9/30 04:06
 * @Description:
 */

@Service("userDetailsService")
public class myUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private GroupsMapper groupsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        Users users = usersMapper.selectOne(wrapper);


        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        System.out.println(users);


        String roles = rolesMapper.selectRolesByUsername(users.getUsername());


        String groups = groupsMapper.selectGroupsByUsername(users.getUsername());
        String Roles;

        if (groups != null && groups.length() > 0)
            Roles = roles + "," + groups;
        else
            Roles = roles;

        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(Roles);
        return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), auths);
    }


}
