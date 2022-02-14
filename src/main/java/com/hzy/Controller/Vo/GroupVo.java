package com.hzy.Controller.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: hzy
 * @Date: 2022/2/14 18:51
 * @Description:
 */
@Data
public class GroupVo {
    private String groupName;

    private String owner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
