package com.doc.cloud.base.service;

import com.doc.cloud.base.dao.InitDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * 初始化git工作区
 * Created by majun on 09/02/2018.
 */
@Service("initGitWorkSpaceService")
public class InitGitWorkSpaceService implements InitializingBean {

    @Value("#{configurer['base.path']}")
    private String path;

    @Autowired
    private InitDao initDao;

    private void createWorkSpace() throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
    }

    private void createDateBase(){
        initDao.createDatabase();
        initDao.insertInitData();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createWorkSpace();
        createDateBase();
    }
}