package com.hzy.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzy.Service.userService;
import com.hzy.entity.Groups;
import com.hzy.entity.Roles;
import com.hzy.entity.Users;
import com.hzy.entity.userGroup;
import com.hzy.mapper.GroupsMapper;
import com.hzy.mapper.RolesMapper;
import com.hzy.mapper.UsersMapper;
import com.hzy.mapper.userGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2021/10/12 10:45
 * @Description:
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class userServiceImpl implements userService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private userGroupMapper userGroupMapper;
    @Autowired
    private GroupsMapper groupsMapper;
    @Autowired
    private modeshapeServiceImpl modeshapeService;
    @Autowired
    private Repository repository;

    @Override
    public Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String Register(Users user) {
        log.info("user == >{}", user);
        if (user == null)
            return "false";
        //查询该用名是否已经被注册
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        Users users = usersMapper.selectOne(wrapper);
        if (users != null)
            return "该用户名已被占用";
        //注册user账号
        int userInsert = usersMapper.insert(user);
        if (userInsert == 0)
            return "异常错误";
        //设置账号角色
        Roles role = new Roles();
        role.setUserName(user.getUsername());
        role.setUserRoles("readwrite");
        int rolesInsert = rolesMapper.insert(role);
        if (rolesInsert == 0)
            return "异常错误";

        userGroup userGroup = new userGroup();
        userGroup.setUserName(user.getUsername());
        userGroup.setGroupName("ShareAll");
        int groupInsert = userGroupMapper.insert(userGroup);
        if (groupInsert == 0)
            return "异常错误";

        userGroup.setGroupName("Literature_library");
        int groupInsert1 = userGroupMapper.insert(userGroup);
        if (groupInsert1 == 0)
            return "异常错误";
        String roName = user.getUsername() + "_Repository";

        Session session = null;
        try {
            session = repository.login();
            Node node = session.getRootNode().addNode(roName);
            log.info("新增用户库==>{}", node.getName());
//            modeshapeService.setPrivilege(session, node, user.getUsername());
//            modeshapeService.setPrivilege(session, node, "ShareAll");
            session.save();
        } catch (Exception e) {
            e.printStackTrace();
            return "错误";
        } finally {
            session.logout();
        }

        return "true";
    }

    @Override
    public boolean createGroup(String groupName, String authority) throws RuntimeException {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        Users selectOne = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", groupName));
        if (selectOne != null)
            throw new RuntimeException("该组名不可用,和其他用户的ID冲突");

        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", groupName));
        if (groups != null)
            throw new RuntimeException("该组名已被占用");
        Groups insertG = new Groups();
        insertG.setGroupName(groupName)
                .setOwner(auth)
                .setAuthority(authority);
        int i = groupsMapper.insert(insertG);

        userGroup userGroup = new userGroup();
        userGroup.setGroupName(groupName)
                .setUserName(auth);
        int j = userGroupMapper.insert(userGroup);

        if (i + j == 2)
            return true;
        else
            throw new RuntimeException("意外出错");
    }

    @Override
    public List<Groups> getMyGroups() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("auth==>{}", auth);
        return groupsMapper.selectList(new QueryWrapper<Groups>().eq("owner", auth));
    }

    @Override
    public List<userGroup> getGroupsForMe() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("auth==>{}", auth);

        return userGroupMapper.selectList(new QueryWrapper<userGroup>().eq("user_name", auth));
    }

    @Override
    public String setGroup(String GroupName, String userName) {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));

        if (groups == null)
            return "该组不存在，请先创建该组";

        if (userName.equals(groups.getOwner()))
            return "组长无需重复加入";
        Users one1 = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", userName));
        System.out.println("one1 = " + one1);
        if (one1 == null)
            return "该用户不存在";
        Groups selectOne = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
        if (selectOne == null)
            return "该组不存在，请先创建该组";
        //验证是否是组长
        if (!selectOne.getOwner().equals(auth))
            return "非组长邀请,请让组长邀请进组";
        userGroup userGroup = new userGroup();
        userGroup.setUserName(userName);
        userGroup.setGroupName(GroupName);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_name", userName);
        hashMap.put("group_name", GroupName);
        userGroup one = userGroupMapper.selectOne(new QueryWrapper<userGroup>().allEq(hashMap));
        if (one != null)
            return "该用户已在该组";
        int insert = userGroupMapper.insert(userGroup);
        if (insert == 0)
            return "失败，请联系管理员进行操作";
        return "true";
    }


    @Override
    public String Disband(String GroupName) {
        if ("ShareAll".equals(GroupName) || "admins".equals(GroupName)||"Literature_library".equals(GroupName))
            return "无权限";
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups selectOne = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
        if (selectOne == null)
            return "该组不存在";
        if (!selectOne.getOwner().equals(auth))
            return "本操作仅限组长操作";
        groupsMapper.deleteById(selectOne.getId());
        userGroupMapper.delete(new QueryWrapper<userGroup>().eq("group_name", GroupName));
        return "true";
    }

    @Override
    public String outGroup(String GroupName) {
        if ("ShareAll".equals(GroupName) || "admins".equals(GroupName)||"Literature_library".equals(GroupName))
            return "无权限";
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
        if (auth.equals(groups.getOwner()))
            return "组长无法退出组,只能解散组";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_name", auth);
        hashMap.put("group_name", GroupName);
        int delete = userGroupMapper.delete(new QueryWrapper<userGroup>().allEq(hashMap));
        if (delete == 0)
            return "退出失败，请检查是否已退出";
        return "true";
    }

    @Override
    public void KickGroup(String groupName, String userName) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        QueryWrapper<Groups> groupsQueryWrapper = new QueryWrapper<>();
        QueryWrapper<userGroup> userGroupQueryWrapper = new QueryWrapper<>();

        Groups groups = groupsMapper.selectOne(groupsQueryWrapper.eq("group_name", groupName));

        if (groups == null)
            throw new RuntimeException("该组不存在");
        if (!name.equals(groups.getOwner()))
            throw new RuntimeException("无权限，本操作只有组长可执行");
        if (userName.equals(groups.getOwner()))
            throw new RuntimeException("无法将自己移出组");
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", userName);
        map.put("group_name", groupName);
        userGroup userGroup = userGroupMapper.selectOne(userGroupQueryWrapper.allEq(map));
        if (userGroup == null)
            throw new RuntimeException("该用户不在本组");
        userGroupMapper.deleteById(userGroup.getId());

    }

    @Override
    public List<userGroup> getGroupInfo(String groupName) {
        return userGroupMapper.selectList(new QueryWrapper<userGroup>().eq("group_name", groupName));
    }

}
