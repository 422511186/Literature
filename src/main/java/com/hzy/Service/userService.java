package com.hzy.Service;

import com.hzy.Controller.Vo.GroupVo;
import com.hzy.Controller.Vo.userVo;
import com.hzy.Controller.model.userInfoModel;
import com.hzy.entity.Groups;
import com.hzy.entity.Users;

import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2021/10/12 10:45
 * @Description:
 */

@SuppressWarnings("JavaDoc")
public interface userService {


    Object getUser();

    /**
     * 注册
     *
     * @param user
     * @return
     */
    String Register(Users user);

    /**
     * 设置账号的更多信息
     * @param model
     * @return
     */
    boolean setUserInfo(userInfoModel model);

    /**
     * 创建组
     * 0、jcr:read	获取Node,属性和内容
     * 1、jcr:modifyProperties	创建，删除，修改Node中属性值.
     * 2、jcr:addChildNodes	创建node的子node.
     * 3、jcr:removeNode	删除一个node
     * 4、jcr:removeChildNodes	删除node的子node。
     * 5、jcr:write	权限组合： jcr:modifyProperties, jcr:addChildNodes, jcr:removeNode, and jcr:removeChildNodes.
     * 6、jcr:readAccessControl	读取node的权限控制设置
     * 7、cr:modifyAccessControl	修改node的权限控制设置
     * 8、jcr:lockManagement	锁/释放node
     * 9、jcr:versionManagement	node版本管理
     * 10、jcr:nodeTypeManagement	添加/删除混合结点类型，修改基本结点类型.
     * 11、jcr:retentionManagement	node保留管理
     * 12、jcr:lifecycleManagement	node生命周期管理
     * 13、jcr:all	权限组合: jcr:read, jcr:write, jcr:readAccessControl, jcr:modifyAccessControl, jcr:lockManagement,
     * jcr:versionManagement, jcr:nodeTypeManagement, jcr:retentionManagement, and jcr:lifecycleManagement
     *
     * @param GroupName
     * @param authority
     * @return
     */
    boolean createGroup(String GroupName, String authority) throws RuntimeException;
    /**
     * 获取当前用户创建的所有组
     *
     * @return
     */
    List<Groups> getMyGroups();

    /**
     * 获取当前用户所在的所有组
     *
     * @return
     */
    List<GroupVo> getGroupsForMe();

    /**
     * 拉人进组，需要组的创建者才能操作
     *
     * @param GroupName
     * @param userName
     * @return
     */
    String setGroup(String GroupName, String userName);

    /**
     * 解散组，仅限组长可操作。
     *
     * @param GroupName
     * @return
     */
    String Disband(String GroupName);

    /**
     * 退出组
     *
     * @param GroupName
     * @return
     */
    String outGroup(String GroupName);

    void KickGroup(String groupName, String userName);

    List<userVo> getGroupInfo(String groupName);
}
