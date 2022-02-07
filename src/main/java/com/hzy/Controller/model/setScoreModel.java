package com.hzy.Controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2021/11/4 12:06
 * @Description:
 */

@ApiModel(value = "评分的数据模型")
public class setScoreModel {
    @ApiModelProperty(value = "文献的id")
    private String identifier;
    @ApiModelProperty(value = "分数")
    private double score;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "setScoreModel{" +
                "identifier='" + identifier + '\'' +
                ", score=" + score +
                '}';
    }
}
