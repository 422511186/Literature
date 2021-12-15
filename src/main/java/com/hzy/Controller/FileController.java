package com.hzy.Controller;

import com.hzy.Controller.model.addAnnotationModel;
import com.hzy.Controller.model.addFileModel;
import com.hzy.Service.Impl.modeshapeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 18:13
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/Api/file")
//@CrossOrigin
public class FileController {
    @Autowired
    private modeshapeServiceImpl modeshapeService;

    @GetMapping("getFileById")
    Map<String, Object> getFileById(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        return modeshapeService.getFileById(nodeIdentifier);
    }


    @PostMapping(value = "addFile")
    Map<String, Object> addFile(@RequestBody addFileModel model) {
        log.debug(model.toString());
        return modeshapeService.addFile(model.getNodeIdentifier(), model.getFileName(), model.getFileUrl());
    }

     @PostMapping(value = "addAnnotation")
    Map<String, Object> addAnnotation(@RequestBody addAnnotationModel model) {
        log.debug(model.toString());
        return modeshapeService.addAnnotation(model);
    }

    @GetMapping(value = "getAnnotations")
    Map<String, Object> getAnnotations(@RequestParam(value = "nodeIdentifier") String nodeIdentifier) {
        log.debug("nodeIdentifier = {}",nodeIdentifier);
        return modeshapeService.getAnnotations(nodeIdentifier);
    }

}
