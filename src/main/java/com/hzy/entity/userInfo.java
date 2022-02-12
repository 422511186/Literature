package com.hzy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * @Auther: hzy
 * @Date: 2022/2/12 18:06
 * @Description:
 */
@TableName(value = "user_info")
@Accessors(chain = true)
public class userInfo {
    @TableId(value = "id")
    private Integer id;

    private String nickName;
    private String unit;
    private String profession;
    private String mail;
    private String telephone;
    private String personalStatement;

    public userInfo() {
    }


    public userInfo(Integer id,
                    String nickName,
                    String unit,
                    String profession,
                    String mail,
                    String telephone,
                    String personalStatement) {
        this.id = id;
        this.nickName = nickName;
        this.unit = unit;
        this.profession = profession;
        this.mail = mail;
        this.telephone = telephone;
        this.personalStatement = personalStatement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }
}
