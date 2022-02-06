package com.hzy.Utils;

import com.hzy.security.SpringSecurityCredentials;
import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.JcrRepository;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.picketbox.factories.SecurityFactory;
import org.springframework.security.core.Authentication;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.net.URL;
import java.util.concurrent.Future;

/**
 * @Auther: hzy
 * @Date: 2021/8/3 16:39
 * @Description:
 */
@Deprecated
public class modeshapeUtil {
    private static ModeShapeEngine modeShapeEngine = null;
    private static JcrRepository repository = null;
    private static Session session = null;
    //存储库的配置文件
    private static final URL URL = modeshapeUtil.class.getClassLoader().getResource("my-repository-config-dev.json");

    /**
     * 静态代码块，存储库引擎启动，加载存储库配置文件，并且检查配置和部署是否存在错误
     */
    static {
        try {
            modeShapeEngine = new ModeShapeEngine();
            if (URL != null) {
                RepositoryConfiguration repositoryConfiguration = null;
                repositoryConfiguration = RepositoryConfiguration.read(URL);
                System.out.println("repositoryConfiguration = " + repositoryConfiguration);
                modeShapeEngine.start();

                SecurityFactory.prepare();

                //验证存储库配置
                Problems problems = repositoryConfiguration.validate();
                if (problems.hasErrors()) {
                    System.err.println("Problems with the configuration.");
                    System.err.println(problems);
                    System.exit(-1);
                }

                // 部署存储库
                repository = modeShapeEngine.deploy(repositoryConfiguration);

                //检查部署问题
                Problems problems1 = repository.getStartupProblems();
                if (problems1.hasErrors() || problems1.hasWarnings()) {
                    System.err.println("Problems deploying the repository.");
                    System.err.println(problems1);
                    System.exit(-1);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认权限登录
     *
     * @return
     */
    public static Session Login() {
        try {
            session = repository.login("default");
            return session;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Session Login(String username, String password) {
        try {
            if (username != null && password != null) {
                session = repository.login(new SimpleCredentials(username, password.toCharArray()), "default");
                return session;
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Session Login(Authentication auth) {
        try {

            Session session = repository.login(new SpringSecurityCredentials(auth));
            return session;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭modeshape引擎
     *
     * @return
     */
    public static boolean shutdown() {
        try {
            Future<Boolean> future = modeShapeEngine.shutdown();
            if (future.get()) {  // blocks until the engine is shutdown
                System.out.println("Shutdown successful");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
