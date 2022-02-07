package com.hzy.Controller.model;



/**
 * @Auther: hzy
 * @Date: 2022/1/30 16:27
 * @Description:
 */

public class commentModel {
    private String id;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "commentModel{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
