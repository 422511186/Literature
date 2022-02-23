package com.hzy.Service;

/**
 * @Auther: hzy
 * @Date: 2022/2/14 21:00
 * @Description:
 */
public interface groupInvitationCodeService {

    /**
     * 生成邀请码
     * @param groupName 团队名
     * @return 生成的
     */
    String generateVerificationCode(String groupName);

    /**
     * 关闭邀请码
     * @param groupName 团队名
     * @return
     */
    boolean closeInviteCode(String groupName);

    /**
     * 通过邀请码获取到对应的团队名
     * @param id
     * @return
     */
    String getGroupById(String id) throws Exception;

    /**
     * 通过邀请码加入到对应的团队
     * @return
     */
    boolean recordByCode(String id) throws Exception;

    /**
     * 验证该团队是否是当前用户创建的
     * @param groupName
     * @return
     */
    boolean isCreate(String groupName) throws Exception;

}
