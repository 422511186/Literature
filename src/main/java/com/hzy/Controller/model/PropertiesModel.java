package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Auther: hz
 * @Date: 2021/10/31 12:03
 * @Description:
 */

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

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getDynamicTags() {
        return dynamicTags;
    }

    public void setDynamicTags(String[] dynamicTags) {
        this.dynamicTags = dynamicTags;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPublicationPlace() {
        return publicationPlace;
    }

    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPageCode() {
        return pageCode;
    }

    public void setPageCode(Integer pageCode) {
        this.pageCode = pageCode;
    }

    public String getMeeting() {
        return meeting;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getEditing() {
        return Editing;
    }

    public void setEditing(String editing) {
        Editing = editing;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PropertiesModel{" +
                "nodeIdentifier='" + nodeIdentifier + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dynamicTags=" + Arrays.toString(dynamicTags) +
                ", publication_date='" + publication_date + '\'' +
                ", volume=" + volume +
                ", period=" + period +
                ", publicationPlace='" + publicationPlace + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pageCode=" + pageCode +
                ", meeting='" + meeting + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                ", Editing='" + Editing + '\'' +
                ", language='" + language + '\'' +
                ", mainTopic='" + mainTopic + '\'' +
                ", importance=" + importance +
                ", cases='" + cases + '\'' +
                ", Comment='" + Comment + '\'' +
                ", score=" + score +
                '}';
    }
}
