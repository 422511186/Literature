package com.example.demo;

import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.JcrRepository;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.picketbox.factories.SecurityFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.concurrent.Future;

/**
 * @Auther: hzy
 * @Date: 2021/7/28 19:33
 * @Description:
 */
public class demo {
    public static void main(String[] args) throws Exception {

        //启动加载引擎
        ModeShapeEngine engine = new ModeShapeEngine();
        engine.start();

        SecurityFactory.prepare();
        //读取存储库的配置文件
        RepositoryConfiguration config = RepositoryConfiguration.read("my-repository-config-dev.json");

        System.out.println("config = " + config);
        System.out.println("config.getName() = " + config.getName());

        //验证存储库配置
        Problems problems = config.validate();
        if (problems.hasErrors()) {
            System.err.println("Problems with the configuration.");
            System.err.println(problems);
            System.exit(-1);
        }
        // 部署存储库
        JcrRepository repository = engine.deploy(config);


        //创建和使用 JCR 会话
        String username = "admin";
//        Session session = repository.login(new SimpleCredentials(new SpringSecurityCredentials(auth));
        Session session = repository.login();
//        Session session = repository.login();
        Node root = session.getRootNode();
//        断言判断
        assert root != null;

        Node a = root.addNode("a");
        a.setProperty("a.key", "a.value");

        root.addNode("b");
        root.addNode("c");
        root.addNode("d");
        session.save();
        session.logout();
        SecurityFactory.release();
        //关闭 ModeShape 引擎，可选择阻塞直到完成
        Future<Boolean> future = engine.shutdown();
        if (future.get()) {  // blocks until the engine is shutdown
            System.out.println("Shutdown successful");
        }
    }


}
