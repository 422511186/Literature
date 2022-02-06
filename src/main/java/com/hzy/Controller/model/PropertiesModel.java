package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: hz
 * @Date: 2021/10/31 12:03
 * @Description:
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "文献类")
public class PropertiesModel implements Serializable {

    @ApiModelProperty(value = "文献的id")
    private String nodeIdentifier;
    @ApiModelProperty(value = "文献的URL")
    private String url;
    @ApiModelProperty(value = "文献的实体文件路径")
    private String path;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "摘要")
    private String summary;
    @ApiModelProperty(value = "关键字")
    private String keyWord;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "种类")
    private String type;
    @ApiModelProperty(value = "标签")
    private String[] dynamicTags;
    @ApiModelProperty(value = "出版年份")
    private String publication_date;
    @ApiModelProperty(value = "卷")
    private Integer volume;
    @ApiModelProperty(value = "期")
    private Integer period;
    @ApiModelProperty(value = "出版地")
    private String publicationPlace;
    @ApiModelProperty(value = "出版社")
    private String publisher;
    @ApiModelProperty(value = "页码")
    private Integer pageCode;
    @ApiModelProperty(value = "会议举办地")
    private String meeting;
    @ApiModelProperty(value = "会议日期")
    private String meetingDate;
    @ApiModelProperty(value = "编辑")
    private String Editing;
    @ApiModelProperty(value = "语种")
    private String language;
    @ApiModelProperty(value = "主题")
    private String mainTopic;
    @ApiModelProperty(value = "重要度")
    private Integer importance;
    @ApiModelProperty(value = "附件")
    private String cases;
//    @ApiModelProperty(value = "批注、笔记")
//    private String notes;
    @ApiModelProperty(value = "评论")
    private String Comment;
    @ApiModelProperty(value = "平均评分")
    private double score;
}
