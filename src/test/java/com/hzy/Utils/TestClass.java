package com.hzy.Utils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Auther: hzy
 * @Date: 2022/1/20 01:38
 * @Description:
 */
@SpringBootTest
public class TestClass {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void  Test01(){
        System.out.println("sqlSessionFactory = " + sqlSessionFactory);
    }
}
