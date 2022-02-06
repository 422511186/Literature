package com.hzy.Controller;

import com.hzy.Service.Impl.modeshapeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * PDF文件上传
 *
 * @Auther: hzy
 * @Date: 2022/1/18 14:35
 * @Description:
 */

@Controller
@RequestMapping(value = "/Api/File")
public class PDFController {
    private final modeshapeServiceImpl modeshapeService;

    //绑定文件上传路径到uploadPath
    @Value("${web.upload-path}")
    private String uploadPath;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
    @Autowired
    public PDFController(modeshapeServiceImpl modeshapeService) {
        this.modeshapeService = modeshapeService;
    }


    @ResponseBody
    @PostMapping(value = "/upload")
    public Map<String, Object> upload(@RequestParam(value = "file") MultipartFile uploadFile,
                                      @RequestParam(value = "id") String id) {
        Map<String, Object> map = new HashMap<>();

        String format = sdf.format(new Date());

        try {

            String oldName = uploadFile.getOriginalFilename();

            String newName =format
                    + UUID.randomUUID().toString()
                    + oldName.substring(oldName.lastIndexOf("."));

            // 文件保存
            uploadFile.transferTo(new File(uploadPath, newName));

            String oldPath = modeshapeService.setPath(id, newName);

            //如果已经存在，删除上次存储的PDF文件，避免磁盘无效占用。
            File file = new File(uploadPath+oldPath);
            if (file.exists()) {
                file.delete();
                System.out.println("===========删除成功=================");

            } else {
                System.out.println("===============删除失败==============");
            }

            map.put("code", 200);
            map.put("msg", "上传文件成功！");

            System.out.println("oldName = " + oldName);
            System.out.println("newName = " + newName);
            System.out.println("uploadPath = " + uploadPath);
        } catch (Exception e) {
            map.put("code", 500);
            map.put("msg", "上传文件失败！");
            e.printStackTrace();
        }
        return map;
    }


}

