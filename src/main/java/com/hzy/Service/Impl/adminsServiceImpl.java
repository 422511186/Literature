package com.hzy.Service.Impl;

import com.hzy.Service.adminsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: hzy
 * @Date: 2021/11/1 20:30
 * @Description:
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class adminsServiceImpl implements adminsService {
    @Autowired
    private modeshapeServiceImpl modeshapeService;
}
