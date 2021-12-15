package com.hzy.Controller;

import com.hzy.Service.Impl.modeshapeServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 16:52
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/Api/node")
@CrossOrigin
public class NodeController {
    @Autowired
    private modeshapeServiceImpl modeshapeService;

    @ApiOperation("获取当前用户节点下的所有节点和文件")
    @GetMapping("getAll")
    Map<String, Object> getAll(
            @ApiParam(value = "节点id,省略则返回用户根目录下的所有信息")
            @RequestParam(value = "nodeIdentifier", required = false) String nodeIdentifier) {
        log.debug(nodeIdentifier);
        if (nodeIdentifier != null) {
            return modeshapeService.getAll(nodeIdentifier);
        } else {
            return modeshapeService.getAll(null);
        }
    }

    @GetMapping("addNode")
    Map<String, Object> addNode(@RequestParam(value = "nodeIdentifier", required = false) String nodeIdentifier,
                                @RequestParam(value = "nodeName") String nodeName) {

        if (nodeIdentifier != null) {
            return modeshapeService.addNode(nodeIdentifier, nodeName,0);
        } else {
            return modeshapeService.addNode(null, nodeName,0);
        }
    }

    @GetMapping("removeNode")
    Map<String, Object> removeNode(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        return modeshapeService.remove(nodeIdentifier);
    }

    @GetMapping("getGroupsOfNode")
    Map<String, Object> getGroupsOfNode(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        return modeshapeService.getGroupsOfNode(nodeIdentifier);
    }
    @GetMapping("delGroupForNode")
    Map<String, Object> delGroupForNode(@RequestParam(value = "groupName") String groupName,
                                        @RequestParam(value = "nodeIdentifier") String nodeIdentifier  ) {
        return modeshapeService.delGroupForNode(nodeIdentifier,groupName);
    }
}
