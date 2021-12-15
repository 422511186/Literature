package com.hzy.Controller;

import com.hzy.Controller.model.setScoreModel;
import com.hzy.Controller.model.userModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import com.hzy.Service.Impl.userServiceImpl;
import com.hzy.entity.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/9/8 11:35
 * @Description:
 */


@Api(value = "用户账号和权限组的相关接口")
@RestController
@RequestMapping(value = "/Api/User")
@CrossOrigin
public class UserController {

    @Autowired
    private userServiceImpl userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private modeshapeServiceImpl modeshapeService;


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
    public Map<String, Object> Register(@RequestBody  userModel user) {
        Map<String, Object> map = new HashMap<>();
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

    @PostMapping(value = "setScore")
    public Map<String,Object> setScore(@RequestBody setScoreModel model){
        System.out.println("model = " + model);
        return modeshapeService.setScore(model.getIdentifier(), model.getScore());
    }



}
