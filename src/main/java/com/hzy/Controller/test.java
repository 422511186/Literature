package com.hzy.Controller;

import com.google.gson.Gson;
import com.hzy.Controller.model.canvasModel;
import com.hzy.Test.A;
import io.swagger.annotations.ApiModel;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2022/1/20 01:40
 * @Description:
 */
@RestController
@RequestMapping(value = "/Api/test")
@ApiModel(value = "接口测试类")
public class Test {

    private  SqlSessionFactory sqlSessionFactory;

    @Autowired
    public Test(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * TODO 验证spring容器中已经含有sqlSessionFactory对象
     * @return
     */
    @GetMapping("Test01")
    public Map<String, Object> getUser() {
        Map<String, Object> map = new HashMap<>();
        System.out.println("sqlSessionFactory = " + sqlSessionFactory);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        System.out.println("sqlSession = " + sqlSession);
        map.put("code", 200);
        return map;
    }
    @GetMapping("Test02")
    public Map Test02() {
        Gson gson = new Gson();
        Map map = gson.fromJson(A.B, Map.class);
        System.out.println("map = " + map);
        return map;
    }
    @PostMapping("Test03")
    public Object Test03(@RequestBody canvasModel model) {
        return model;
    }
}

