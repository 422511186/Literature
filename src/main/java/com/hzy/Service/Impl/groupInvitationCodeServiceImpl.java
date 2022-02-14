package com.hzy.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hzy.Service.groupInvitationCodeService;
import com.hzy.entity.groupInvitationCode;
import com.hzy.entity.userGroup;
import com.hzy.mapper.UsersMapper;
import com.hzy.mapper.groupInvitationCodeMapper;
import com.hzy.mapper.userGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: hzy
 * @Date: 2022/2/14 21:00
 * @Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class groupInvitationCodeServiceImpl implements groupInvitationCodeService {

    @Autowired
    private groupInvitationCodeMapper groupInvitationCodeMapper;
    @Autowired
    private userGroupMapper userGroupMapper;

    /**
     * 生成邀请码
     *
     * @param groupName 团队名
     * @return 生成的
     */
    @Override
    public String generateVerificationCode(String groupName) {
        //根据雪花算法生成id，作为团队唯一的邀请码
        long id = IdWorker.getId();
        //查看数据库表中是否已经含有该id(code)
        QueryWrapper<groupInvitationCode> queryWrapperCount = new QueryWrapper<>();
        queryWrapperCount.eq("code",String.valueOf(id));
        int selectCount = groupInvitationCodeMapper.selectCount(queryWrapperCount);
        //如果已经含有，则重新生成id，并且重新查阅
        while (selectCount!=0){
            id = IdWorker.getId();
            queryWrapperCount.eq("code",String.valueOf(id));
            selectCount = groupInvitationCodeMapper.selectCount(queryWrapperCount);
        }

        log.info("id = {}",id);

        //查找该表中是否已有该团队的邀请码信息
        QueryWrapper<groupInvitationCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_name",groupName);
        groupInvitationCode selectOne = groupInvitationCodeMapper.selectOne(queryWrapper);

        System.out.println("selectOne = " + selectOne);

        //如果该团队没有生成过邀请码，则插入一条信息
        if (selectOne==null){
            groupInvitationCodeMapper.insert(new groupInvitationCode(groupName,String.valueOf(id) ));
        }else {//如果该团队生成过邀请码，则更新邀请码
            UpdateWrapper<groupInvitationCode> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("group_name",groupName);
            groupInvitationCodeMapper.update(new groupInvitationCode(groupName,String.valueOf(id) ),updateWrapper);
        }
        return String.valueOf(id);
    }

    /**
     * 关闭邀请码
     *
     * @param groupName 团队名
     * @return
     */
    @Override
    public boolean closeInviteCode(String groupName) {
        //查找该表中是否已有该团队的邀请码信息
        QueryWrapper<groupInvitationCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_name",groupName);
        groupInvitationCode selectOne = groupInvitationCodeMapper.selectOne(queryWrapper);
        if(selectOne!=null){
            int delete = groupInvitationCodeMapper.delete(queryWrapper);
            return true;
        }
        return false;
    }


    /**
     * 通过邀请码获取到对应的团队名
     * @param id
     * @return
     */
    @Override
    public String getGroupById(String id) throws Exception {
        QueryWrapper<groupInvitationCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",String.valueOf(id));
        groupInvitationCode selectOne = groupInvitationCodeMapper.selectOne(queryWrapper);
        log.info(selectOne+"");
        if (selectOne!=null||!selectOne.getGroupName().isEmpty())
            return selectOne.getGroupName();
        else {
            throw new Exception("该邀请码已失效");
        }
    }

    /**
     * 通过邀请码加入到对应的团队
     * @return
     */
    @Override
    public boolean recordByCode(String id) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String groupName = getGroupById(id);
        QueryWrapper<userGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        queryWrapper.eq("group_name",groupName);
        userGroup userGroup = userGroupMapper.selectOne(queryWrapper);
        //如果不为空，代表本用户已经在该团队中
        if (userGroup!=null){
            throw new Exception("你已经在该团队中");
        }
        int insert = userGroupMapper.insert(new userGroup() {{
            setGroupName(groupName);
            setUserName(userName);
        }});
        //插入条数为0时，表示插入数据失败
        if (insert==0) {
            return false;
        }
        return true;
    }


}
