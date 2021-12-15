package com.hzy.Properties;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: hzy
 * @Date: 2021/10/25 12:22
 * @Description:
 */
@Data
@Builder
public class AccessToken {
    private String loginAccount;
    private String token;
    private Date expirationTime;
}