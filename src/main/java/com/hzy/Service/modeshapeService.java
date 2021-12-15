package com.hzy.Service;

import com.hzy.Controller.model.PropertiesModel;
import com.hzy.Controller.model.addAnnotationModel;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/8/4 19:12
 * @Description:
 */

public interface modeshapeService {
    //登录
    Session Login();
    //文献库管理的的服务
    //获得文献库中所有文献信息
    Map<String, Object> getFileInfoAll();

    //添加文献信息
    Map<String, Object> addFileInfo(PropertiesModel model);

    //修改文献信息
    Map<String, Object> updateFileInfo(PropertiesModel model);

    //根据id获得所对应的文献信息
    Map<String, Object> getFileInfoByID(String nodeIdentifier);

    //根据id删除文献库中所对应的文献
    Map<String, Object> deleteFileInfoByID(String nodeIdentifier);

    //为文献评分
    Map<String, Object> setScore(String nodeIdentifier, double score);


    //用户个人库服务
    //获取文件夹下所有
    Map<String, Object> getAll(String nodeIdentifier);

    //新建文件夹
    Map<String, Object> addNode(String nodeIdentifier, String nodeName, int f);

    //添加文件
    Map<String, Object> addFile(String nodeIdentifier, String fileName, String fileUrl);

    //取得引用节点中的真实url和其他文献信息
    Map<String, Object> getFileById(String nodeIdentifier);

    //删除文件夹或文件
    Map<String, Object> remove(String nodeIdentifier);

    //权限管理
    boolean setPrivilege(Session session, Node node, String GroupName);

    //为某个节点设置权限组
    Map<String, Object> permissionsForNode(String nodeIdentifier, String groupName);

    //协同阅读模块
    //获得所有的团队库
    Map<String, Object> getAllTeam(String nodeIdentifier);

    //创建团队库
    boolean create_Team_Node(String groupName, String nodeName);

    //创建团队服务
    Map<String, Object> create_Team(String groupName, String nodeName);

    //获取库下所有团队
    Map<String, Object> getGroupsOfNode(String nodeIdentifier);

    //删除团队和团队库存的关联关系
    Map<String, Object> delGroupForNode(String nodeIdentifier, String groupName);
    //删除文献
    Map<String, Object> removeCollaboration(String nodeIdentifier);
    //为文献添加批注
    Map<String, Object> addAnnotation(addAnnotationModel model);
    //获得文献批注
    Map<String, Object> getAnnotations(String nodeIdentifier);



}
