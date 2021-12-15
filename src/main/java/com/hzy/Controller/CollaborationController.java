package com.hzy.Controller;

import com.hzy.Controller.model.KickModel;
import com.hzy.Controller.model.permissionsForNodeModel;
import com.hzy.Controller.model.setGroupModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import com.hzy.Service.Impl.userServiceImpl;
import com.hzy.entity.Groups;
import com.hzy.entity.userGroup;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/11/4 10:59
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/Api/Collaboration")
//@CrossOrigin
public class CollaborationController {

    @Autowired
    private userServiceImpl userService;
    @Autowired
    private modeshapeServiceImpl modeshapeService;

    @ApiOperation("获取所有的可见团队库")
    @GetMapping(value = "getAllTeam")
    public Map<String, Object> getAllTeam(@ApiParam(value = "节点id,省略则返回用户根目录下的所有信息")
                                              @RequestParam(value = "nodeIdentifier", required = false) String nodeIdentifier) {
        if (nodeIdentifier != null)
            return modeshapeService.getAllTeam(nodeIdentifier);
        else
            return modeshapeService.getAllTeam(null);
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

    @ApiOperation("创建团队")
    @GetMapping(value = "createGroup")
    public Map<String, Object> createGroup(@RequestParam(value = "groupName") @ApiParam(value = "团队名")  String groupName) {
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

    @ApiOperation("创建团队库")
    @GetMapping(value = "create_Team_Node")
    public Map<String, Object> create_Team_Node(@RequestParam(value = "groupName") @ApiParam(value = "团队名")  String groupName) {
        Map<String, Object> map = map = new HashMap<>();
        try {
            modeshapeService.create_Team_Node(null,groupName);
            map.put("code", 200);
            map.put("msg", "创建成功");
        } catch (Exception e) {
            map.put("code", 403);
            map.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    @ApiOperation("为某个团队库设置团队(仅限创建者操作)")
    @PostMapping(value = "permissionsForNode")
    public Map<String, Object> permissionsForNode(@RequestBody permissionsForNodeModel model) {
        return modeshapeService.permissionsForNode(model.getNodeIdentifier(), model.getGroupName());
    }



    @ApiOperation("获取该账户创建的所有团队")
    @GetMapping(value = "getMyGroups")
    public Map<String, Object> getMyGroups() {
        Map<String, Object> map = new HashMap<>();

        List<Groups> myGroups =
                userService.getMyGroups();
        log.info(myGroups.toString());
        map.put("code", 200);
        map.put("GroupList", myGroups);

        return map;
    }

    @ApiOperation("获得本用户所在的所有的团队")
    @GetMapping(value = "getGroupsForMe")
    public Map<String, Object> getGroupsForMe() {
        Map<String, Object> map = new HashMap<>();

        List<userGroup> getGroupsForMeList =
                userService.getGroupsForMe();
        map.put("code", 200);
        map.put("getGroupsForMeList", getGroupsForMeList);

        return map;
    }

    @ApiOperation("邀请进团队")
    @PostMapping(value = "setGroup")
    public Map<String, Object> setGroup(@RequestBody setGroupModel setGroupOV) {
        log.info(setGroupOV.toString());
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
            log.error(e.getMessage());
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

    @GetMapping("removeCollaboration")
    Map<String, Object> removeCollaboration(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        return modeshapeService.removeCollaboration(nodeIdentifier);
    }
}
