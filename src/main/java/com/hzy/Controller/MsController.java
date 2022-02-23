package com.hzy.Controller;

import com.hzy.Controller.model.PropertiesModel;
import com.hzy.Controller.model.canvasModel;
import com.hzy.Controller.model.commentModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2022/1/15 17:49
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/Api/List")
@CrossOrigin
public class MsController {

    private final modeshapeServiceImpl modeshapeService;

    @Autowired
    public MsController(modeshapeServiceImpl modeshapeService) {
        this.modeshapeService = modeshapeService;
    }

    @ApiOperation("获取列表信息")
    @GetMapping(value = "getList/{Type}")
    public Map<String, Object> getList(@PathVariable("Type") String Type,
                                       @RequestParam(value = "sort", defaultValue = "0") @ApiParam(value = "按评分排序") String sort,
                                       @RequestParam(value = "id", required = false) @ApiParam(value = "子库的id") String id) {
//        log.info("Type = {} ,id = {}", Type ,id);

        Map<String, Object> map;
        if ("1".equals(Type)) {
            map = modeshapeService.getFileInfoAll(sort);
        } else if ("2".equals(Type))
            map = modeshapeService.getAll(null,sort);
        else if ("3".equals(Type))
            map = modeshapeService.getAllTeam(id,sort);
        else {
            map = new HashMap<>();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }


    @ApiOperation("添加文献信息")
    @PostMapping(value = "addFile/{Type}")
    public Map<String, Object> addFile(@PathVariable("Type") @ApiParam(value = "存储库类型 1:公共存储库、2：个人存储库、3：团队存储库") String Type,
                                       @RequestParam(value = "id", required = false) @ApiParam(value = "团队库的id") String id,
                                       @RequestBody PropertiesModel model) {
        log.info("Type = {},id = {},model = {}", Type, id, model.toString());

        Map<String, Object> map;
        if (Arrays.asList("1", "2", "3").contains(Type))
            map = modeshapeService.addFileInfo(Type, id, model);
        else {
            map = new HashMap<>();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    @ApiOperation("获取文献信息By id")
    @GetMapping("getFileInfoByID")
    Map<String, Object> getFileInfoByID(@RequestParam(value = "id") @ApiParam(value = "存储库类型") String nodeIdentifier) {
        System.out.println("nodeIdentifier = " + nodeIdentifier);
        return modeshapeService.getFileInfoByID(nodeIdentifier);
    }

    @ApiOperation("删除文献")
    @GetMapping(value = "delete")
    public Map<String, Object> delete(@RequestParam(value = "id") String id) {
        if (id.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数");
            return map;
        }
        return modeshapeService.removeCollaboration(id);
    }

    @ApiOperation("修改文献")
    @PostMapping(value = "update")
    public Map<String, Object> update(@RequestParam(value = "id") String id, @RequestBody PropertiesModel model) {
        HashMap<String, Object> map = new HashMap<>();
        if (id.isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        String update;
        try {
            update = modeshapeService.update(model, id);
            map.put("code", 200);
            map.put("msg", update);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @ApiOperation("修改文献标签")
    @PostMapping(value = "updateDynamicTags")
    public Map<String, Object> updateDynamicTags(@RequestParam(value = "id") String id,
                                                 @RequestBody PropertiesModel model) {
        HashMap<String, Object> map = new HashMap<>();
        if (id.isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        String update;
        try {
            update = modeshapeService.updateDynamicTags(model, id);
            map.put("code", 200);
            map.put("msg", update);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }


    @ApiOperation("为文献评分")
    @GetMapping(value = "setScore")
    public Map<String, Object> setScore(@RequestParam(value = "id") String id,
                                        @RequestParam(value = "score") Double score) {
        if (id.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", 500);
            map.put("msg", "参数不可为空");
            return map;
        }
        return modeshapeService.setScore(id, score);
    }

    @ApiOperation("保存评论")
    @PostMapping(value = "setComment")
    public Map<String, Object> setComment(@RequestBody commentModel model) {
        System.out.println("model = " + model);
        HashMap<String, Object> map = new HashMap<>();
        if (model.getId().isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        String value;
        try {
            value = modeshapeService.setComment(model.getId(),model.getComment());
            map.put("code", 200);
            map.put("msg", value);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }
    @ApiOperation("获取文献的评论")
    @GetMapping(value = "getComment")
    public Map<String, Object> getComment(@RequestParam(value = "id") String id) {
        HashMap<String, Object> map = new HashMap<>();
        if (id.isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        String value;
        try {
            value = modeshapeService.getComment(id);
            map.put("code", 200);
            map.put("msg", value);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }
    @ApiOperation("保存文献笔记")
    @PostMapping(value = "setNotes")
    public Map<String, Object> setNotes(@RequestParam(value = "id") String id,
                                        @RequestParam(value = "pageNum") Integer pageNum,
                                        @RequestBody Object Notes) {
        HashMap<String, Object> map = new HashMap<>();
        if (id.isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        String value;
        try {
            value = modeshapeService.setNotes(id,pageNum,Notes);
            map.put("code", 200);
            map.put("msg", value);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }
    @ApiOperation("获取文献笔记")
    @GetMapping(value = "getNotes")
    public Map<String, Object> getNotes(@RequestParam(value = "id") String id,
             @RequestParam(value = "pageNum") Integer pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        if (id.isEmpty()) {
            map.put("code", 500);
            map.put("msg", "id为必须传入的参数,不可为空!");
            return map;
        }
        Object value;
        try {
            value = modeshapeService.getNotes(id,pageNum);
            map.put("code", 200);
            map.put("data", value);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());
        }
        return map;
    }


}
