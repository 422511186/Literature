package com.hzy.Controller;

import com.hzy.Service.Impl.groupInvitationCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2022/2/14 20:59
 * @Description:
 */
@RestController
@RequestMapping(value = "/Api/InvitationCode")
public class InvitationCodeController {

    @Autowired
    private groupInvitationCodeServiceImpl service;


    @GetMapping(value = "generateVerificationCode")
    public Map<String, Object> generateVerificationCode(@RequestParam(value = "groupName") String groupName){
        HashMap<String, Object> map = new HashMap<>();
        String code = service.generateVerificationCode(groupName);
        map.put("code",200);
        map.put("data",code);
        return map;
    }
    @GetMapping(value = "closeInviteCode")
    public Map<String, Object> closeInviteCode(@RequestParam(value = "groupName") String groupName){
        HashMap<String, Object> map = new HashMap<>();
        boolean result = service.closeInviteCode(groupName);
        if (result){
            map.put("code",200);
            map.put("msg","succeed");
        }else {
            map.put("code",404);
            map.put("msg","该邀请码已被清除,当前为空");
        }
        return map;
    }

    @GetMapping(value = "recordByCode")
    public Map<String, Object> recordByCode(@RequestParam(value = "code") String code){
        HashMap<String, Object> map = new HashMap<>();
        try {
            boolean record = service.recordByCode(code);
            if (record){
                map.put("code",200);
                map.put("msg","succeed");
            }
            else {
                map.put("code",500);
                map.put("msg","发生异常失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",403);
            map.put("msg",e.getMessage());
        }
        return map;
    }


}
