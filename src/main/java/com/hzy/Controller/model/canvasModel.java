package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 19:09
 * @Description:
 */

@ApiModel(value = "pdf.js中画布canvas的接收参数")
public class canvasModel {

    private String version;
    private List<Objects> objects;
    public void setVersion(String version) {
         this.version = version;
     }
     public String getVersion() {
         return version;
     }

    public void setObjects(List<Objects> objects) {
         this.objects = objects;
     }
     public List<Objects> getObjects() {
         return objects;
     }

}