package com.hzy.Controller;

import com.hzy.Controller.model.KickModel;
import com.hzy.Controller.model.setGroupModel;
import com.hzy.Controller.model.userModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import com.hzy.Service.Impl.userServiceImpl;
import com.hzy.entity.Groups;
import com.hzy.entity.Users;
import com.hzy.entity.userGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/9/8 11:35
 * @Description:
 */


@Api(value = "用户账号和权限组的相关接口")
@RestController
@RequestMapping(value = "/Api/User")
public class UserController {

    private final userServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final modeshapeServiceImpl modeshapeService;

    @Autowired
    public UserController(userServiceImpl userService, AuthenticationManager authenticationManager, modeshapeServiceImpl modeshapeService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.modeshapeService = modeshapeService;
    }

    @ApiOperation("获得当前用户信息")
    @GetMapping("getUser")
    public Map<String, Object> getUser() {
        Map<String, Object> map = new HashMap<>();
        Object user = userService.getUser();
        map.put("code", 200);
        map.put("data", user);
        return map;
    }

    @ApiOperation("用户注册接口")
    @PostMapping(value = "register")
    public Map<String, Object> Register(@RequestBody userModel user) {
        Map<String, Object> map = new HashMap<>();

        //如果用户名或密码长度小于10位
        if(user.getUsername().length()<=10||user.getPassword().length()<=10){
            map.put("code",500);
            map.put("msg","用户名或者密码不符合要求");
            return map;
        }

        String register = userService.Register(new Users(user.getUsername(), user.getPassword()));
        if (register.equals("true")) {
            map.put("code", 200);
            map.put("msg", user.getUsername() + "成功注册");
        } else {
            map.put("code", 403);
            map.put("msg", register);
        }
        return map;
    }


    @ApiOperation("获取该账户创建的所有团队")
    @GetMapping(value = "getMyGroups")
    public Map<String, Object> getMyGroups() {
        Map<String, Object> map = new HashMap<>();

        List<Groups> myGroups =
                userService.getMyGroups();
        map.put("code", 200);
        map.put("data", myGroups);

        return map;
    }

    @ApiOperation("获得本用户所在的所有的团队")
    @GetMapping(value = "getGroupsForMe")
    public Map<String, Object> getGroupsForMe() {
        Map<String, Object> map = new HashMap<>();

        List<userGroup> getGroupsForMeList = userService.getGroupsForMe();
        map.put("code", 200);
        map.put("getGroupsForMeList", getGroupsForMeList);

        return map;
    }

    @ApiOperation("创建团队")
    @GetMapping(value = "createGroup")
    public Map<String, Object> createGroup(@RequestParam(value = "groupName") @ApiParam(value = "团队名") String groupName) {
        Map<String, Object> map = map = new HashMap<>();
        try {
            userService.createGroup(groupName, "0,5");
            map.put("code", 200);
            map.put("msg", "创建成功");
        } catch (Exception e) {
            map.put("code", 403);
            map.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @ApiOperation("邀请进团队")
    @PostMapping(value = "setGroup")
    public Map<String, Object> setGroup(@RequestBody setGroupModel setGroupOV) {

        Map<String, Object> map = new HashMap<>();

        String s = userService.setGroup(setGroupOV.getGroupName(), setGroupOV.getUserName());

        if ("true".equals(s)) {
            map.put("code", 200);
            map.put("msg", setGroupOV.getUserName() + "成功进入" + setGroupOV.getGroupName() + "组");
        } else {
            map.put("code", 403);
            map.put("msg", s);
        }
        return map;
    }

    @ApiOperation("解散团队（仅限自己创建的团队）")
    @GetMapping(value = "Disband")
    public Map<String, Object> Disband(@RequestParam(value = "groupName") @ApiParam("要解散的组名") String groupName) {
        System.out.println("GroupName = " + groupName);
        Map<String, Object> map = new HashMap<>();

        String disband = userService.Disband(groupName);
        if ("true".equals(disband)) {
            map.put("code", 200);
            map.put("msg", "已解散");
        } else {
            map.put("code", 403);
            map.put("msg", disband);
        }

        return map;
    }

    @ApiOperation("退出团队")
    @GetMapping(value = "outGroup")
    public Map<String, Object> outGroup(@RequestParam(value = "groupName") @ApiParam("要退出的组名") String groupName) {
        System.out.println("GroupName = " + groupName);
        Map<String, Object> map = new HashMap<>();

        String outGroup = userService.outGroup(groupName);
        if ("true".equals(outGroup)) {
            map.put("code", 200);
            map.put("msg", "已退出");
        } else {
            map.put("code", 403);
            map.put("msg", outGroup);
        }

        return map;
    }

    @ApiOperation("将某人移出某团队")
    @PostMapping(value = "KickGroup")
    public Map<String, Object> KickGroup(@RequestBody KickModel model) {
        Map<String, Object> map = new HashMap<>();

        String userName = model.getUserName();
        String groupName = model.getGroupName();
        try {
            userService.KickGroup(groupName, userName);
            map.put("code", 200);
            map.put("msg", "succeed");
        } catch (Exception e) {
            map.put("code", 403);
            map.put("msg", "移除失败");
            map.put("error", e.getMessage());
        }

        return map;
    }

    @ApiOperation("获得某团队成员信息")
    @GetMapping(value = "getGroupInfo")
    public Map<String, Object> getGroupInfo(@RequestParam(value = "groupName") String groupName) {
        Map<String, Object> map = new HashMap<>();

        List<userGroup> groupInfo = userService.getGroupInfo(groupName);
        map.put("code", 200);
        map.put("msg", groupInfo);

        return map;
    }

    @ApiOperation("创建团队并创建团队库")
    @GetMapping(value = "createGroupAndNode")
    public Map<String, Object> createGroupAndNode(@RequestParam(value = "groupName") String groupName,
                                           @RequestParam(value = "nodeName") String nodeName) {
        Map<String, Object> team = null;
        try {
            team = modeshapeService.create_Team(groupName, nodeName);
        } catch (Exception e) {
            team = new HashMap<>();
            team.put("code", 403);
            team.put("error", e.getMessage());
            e.printStackTrace();
        }
        return team;
    }

}
