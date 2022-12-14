package com.hzy.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzy.Controller.Vo.GroupVo;
import com.hzy.Controller.Vo.userVo;
import com.hzy.Controller.model.userInfoModel;
import com.hzy.Service.userService;
import com.hzy.entity.*;
import com.hzy.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Arrays;
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
    private userInfoMapper userInfoMapper;
    @Autowired
    private modeshapeServiceImpl modeshapeService;
    @Autowired
    private Repository repository;

    @Override
    public Object getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Users userOne = usersMapper.selectOne(wrapper);
        userInfo userInfo = userInfoMapper.selectById(userOne.getId());

        userVo userVo = new userVo();
        userVo.setAccount(userOne.getUsername());
        userVo.setNickName(userInfo.getNickName());
        userVo.setMail(userInfo.getMail());
        userVo.setProfession(userInfo.getProfession());
        userVo.setUnit(userInfo.getUnit());
        userVo.setTelephone(userInfo.getTelephone());
        userVo.setPersonalStatement(userInfo.getPersonalStatement());

//        log.info("userVo={}",userVo);
        return userVo;
    }

    @Override
    public String Register(Users user) {
        log.info("user == >{}", user);
        if (user == null)
            return "false";
        //????????????????????????????????????
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        Users users = usersMapper.selectOne(wrapper);
        if (users != null)
            return "????????????????????????";
        //??????user??????
        int userInsert = usersMapper.insert(user);

        log.info(user.toString());
        userInfoMapper.insert(new userInfo(user.getId(),
                null,
                null,
                null,
                null,
                null,
                null));

        if (userInsert == 0)
            return "????????????";
        //??????????????????
        Roles role = new Roles();
        role.setUserName(user.getUsername());
        role.setUserRoles("readwrite");
        int rolesInsert = rolesMapper.insert(role);
        if (rolesInsert == 0)
            return "????????????";

        userGroup userGroup = new userGroup();
        userGroup.setUserName(user.getUsername());
        userGroup.setGroupName("ShareAll");
        int groupInsert = userGroupMapper.insert(userGroup);
        if (groupInsert == 0)
            return "????????????";

        userGroup.setGroupName("Literature_library");
        int groupInsert1 = userGroupMapper.insert(userGroup);
        if (groupInsert1 == 0)
            return "????????????";
        String roName = user.getUsername() + "_Repository";

        Session session = null;
        try {
            session = repository.login();
            Node node = session.getRootNode().addNode(roName);
            log.info("???????????????==>{}", node.getName());

//            ?????????????????????????????????
//            modeshapeService.setPrivilege(session, node, user.getUsername());
//            modeshapeService.setPrivilege(session, node, "ShareAll");
            
            session.save();
        } catch (Exception e) {
            e.printStackTrace();
            return "??????";
        } finally {
            session.logout();
        }

        return "true";
    }

    @Override
    public boolean setUserInfo(userInfoModel model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Users userOne = usersMapper.selectOne(wrapper);

        log.info("id = {}",userOne.getId());
        userInfo userInfo = new userInfo();
        userInfo.setId(userOne.getId());

        userInfo.setNickName(model.getNickName());
        userInfo.setUnit(model.getUnit());
        userInfo.setProfession(model.getProfession());
        userInfo.setMail(model.getMail());
        userInfo.setTelephone(model.getTelephone());
        userInfo.setPersonalStatement(model.getPersonalStatement());

        int update = userInfoMapper.updateById(userInfo);
        if (update==0)
            return false;
        return true;
    }

    @Override
    public boolean createGroup(String groupName, String authority) throws RuntimeException {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        Users selectOne = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", groupName));
        if (selectOne != null)
            throw new RuntimeException("??????????????????,??????????????????ID??????");

        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", groupName));
        if (groups != null)
            throw new RuntimeException("?????????????????????");

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
            throw new RuntimeException("????????????");
    }

    @Override
    public List<Groups> getMyGroups() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("auth==>{}", auth);
        return groupsMapper.selectList(new QueryWrapper<Groups>().eq("owner", auth));
    }

    @Override
    public List<GroupVo> getGroupsForMe() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("auth==>{}", auth);
        //????????????????????????????????????
        List<userGroup> lists = userGroupMapper.selectList(new QueryWrapper<userGroup>().eq("user_name", auth));
        //???????????????????????????
        List<Groups> owner = groupsMapper.selectList(new QueryWrapper<Groups>().eq("owner", auth));

        List<String> ownerNameList = new ArrayList<>();
        for (Groups e:owner){
            log.info(e.getGroupName());
            ownerNameList.add(e.getGroupName());
        }

        ArrayList<userGroup> arrayList = new ArrayList<>();

        for (userGroup list : lists) {
            if (!list.getGroupName().equals("ShareAll")&&
                    !list.getGroupName().equals("Literature_library")&&
                    !ownerNameList.contains(list.getGroupName())//???????????????????????????????????????
            ){
                arrayList.add(list);
            }
        }

        ArrayList<GroupVo> list = new ArrayList<>();
        arrayList.forEach(e->{
            Groups one = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", e.getGroupName()));
            GroupVo vo = new GroupVo();
            vo.setGroupName(e.getGroupName());
            vo.setCreateTime(one.getCreateTime());
            vo.setOwner(one.getOwner());
            list.add(vo);
        });

        return list;
    }

    @Override
    public String setGroup(String GroupName, String userName) {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));

        if (groups == null)
            return "????????????????????????????????????";

        if (userName.equals(groups.getOwner()))
            return "????????????????????????";
        Users one1 = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", userName));
        System.out.println("one1 = " + one1);
        if (one1 == null)
            return "??????????????????";
        Groups selectOne = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
        if (selectOne == null)
            return "????????????????????????????????????";
        //?????????????????????
        if (!selectOne.getOwner().equals(auth))
            return "???????????????,????????????????????????";
        userGroup userGroup = new userGroup();
        userGroup.setUserName(userName);
        userGroup.setGroupName(GroupName);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_name", userName);
        hashMap.put("group_name", GroupName);
        userGroup one = userGroupMapper.selectOne(new QueryWrapper<userGroup>().allEq(hashMap));
        if (one != null)
            return "?????????????????????";
        int insert = userGroupMapper.insert(userGroup);
        if (insert == 0)
            return "???????????????????????????????????????";
        return "true";
    }


    @Override
    public String Disband(String GroupName) {
        if (Arrays.asList("ShareAll", "admins", "Literature_library").contains(GroupName))
            return "?????????";
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups selectOne
                = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));
        if (selectOne == null)
            return "???????????????";
        if (!selectOne.getOwner().equals(auth))
            return "???????????????????????????";
        String nodeId = selectOne.getNodeId();

        modeshapeService.removeCollaboration(nodeId);
        log.info("?????????{}???????????????",nodeId);


        groupsMapper.deleteById(selectOne.getId());
        userGroupMapper.delete(new QueryWrapper<userGroup>().eq("group_name", GroupName));

        return "true";
    }

    @Override
    public String outGroup(String GroupName) {
        if ("ShareAll".equals(GroupName) || "admins".equals(GroupName)||"Literature_library".equals(GroupName))
            return "?????????";
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        Groups groups = groupsMapper.selectOne(new QueryWrapper<Groups>().eq("group_name", GroupName));

        if (auth.equals(groups.getOwner()))
            return "?????????????????????,???????????????";

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_name", auth);
        hashMap.put("group_name", GroupName);
        int delete = userGroupMapper.delete(new QueryWrapper<userGroup>().allEq(hashMap));
        if (delete == 0)
            return "???????????????????????????????????????";
        return "true";
    }

    @Override
    public void KickGroup(String groupName, String userName) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        QueryWrapper<Groups> groupsQueryWrapper = new QueryWrapper<>();
        QueryWrapper<userGroup> userGroupQueryWrapper = new QueryWrapper<>();

        Groups groups = groupsMapper.selectOne(groupsQueryWrapper.eq("group_name", groupName));

        if (groups == null)
            throw new RuntimeException("???????????????");
        if (!name.equals(groups.getOwner()))
            throw new RuntimeException("??????????????????????????????????????????");
        if (userName.equals(groups.getOwner()))
            throw new RuntimeException("????????????????????????");
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", userName);
        map.put("group_name", groupName);
        userGroup userGroup = userGroupMapper.selectOne(userGroupQueryWrapper.allEq(map));
        if (userGroup == null)
            throw new RuntimeException("?????????????????????");
        userGroupMapper.deleteById(userGroup.getId());

    }

    @Override
    public List<userVo> getGroupInfo(String groupName) {
        List<userVo> list = userGroupMapper.getGroupUserInfos(groupName);



        return list;
    }

}
