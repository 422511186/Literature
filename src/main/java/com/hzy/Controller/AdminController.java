package com.hzy.Controller;


import com.hzy.Controller.model.PropertiesModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/9/10 16:23
 * @Description:
 */
@RestController
@RequestMapping(value = "/Api/admins")
//@CrossOrigin
public class AdminController {

    @Autowired
    private modeshapeServiceImpl modeshapeService;

    @PostMapping("addFileInfo")
    Map<String, Object> addFileInfo(@RequestBody PropertiesModel model) {
        return modeshapeService.addFileInfo(model);
    }

    @GetMapping("getFileInfoByID")
    Map<String, Object> getFileInfoByID(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        System.out.println("nodeIdentifier = " + nodeIdentifier);
        return modeshapeService.getFileInfoByID(nodeIdentifier);
    }

    @GetMapping("getFileInfoAll")
    Map<String, Object> getFileInfoAll() {
        return modeshapeService.getFileInfoAll();
    }

    @GetMapping("deleteFileInfoByID")
    Map<String, Object> deleteFileInfoByID(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        return modeshapeService.deleteFileInfoByID(nodeIdentifier);
    }

}
