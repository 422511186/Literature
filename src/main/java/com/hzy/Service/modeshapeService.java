package com.hzy.Service;

import com.hzy.Controller.model.PropertiesModel;
import com.hzy.Controller.model.addAnnotationModel;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
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
    Map<String, Object> getFileInfoAll(String sort);

    //添加文献信息
    Map<String, Object> addFileInfo(String Type,String id,PropertiesModel model);

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
    Map<String, Object> getAll(String nodeIdentifier,String sort);

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
    Map<String, Object> getAllTeam(String nodeIdentifier, String sort);

    //创建团队库
    String create_Team_Node(String groupName, String nodeName);

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

    /**
     *  -----------------------------------------------------------
     *  以上部分代码都是狗屎，删除是不敢删除的，
     *  删除炸了咋办！
     *  只能看看有没有什么可重用的代码了！
     *   -----------------------------------------------------------
     */

    /**
     * 修改文献信息
     * @param model
     * @param id
     * @return
     * @throws Exception
     */

  String update(PropertiesModel model,String id) throws Exception;

  /**
     * 修改文献标签
     * @param model
     * @param id
     * @return
     * @throws Exception
     */

  String updateDynamicTags(PropertiesModel model,String id) throws Exception;

    /**
     * 保存评论
     * @param id
     * @param Comment
     * @return
     */
  String setComment(String id,String Comment) throws Exception;
    /**
     * 保存文献笔记
     * @param id
     * @param Notes
     * @return
     */
    String setNotes(String id, Integer pageNum, Object Notes) throws Exception;

    /**
     * 保存文献PDF路径
     * @param id
     * @param Path
     * @return
     */
    String setPath(String id,String Path) throws Exception;

    /**
     * 获取文献的笔记
     * @param id    文献的id
     * @param pageNum    页码
     * @return
     */
    Object getNotes(String id,Integer pageNum) throws Exception;
    /**
     * 获取文献的评论
     * @param id    文献的id
     */
    String getComment(String id) throws RepositoryException;
}
