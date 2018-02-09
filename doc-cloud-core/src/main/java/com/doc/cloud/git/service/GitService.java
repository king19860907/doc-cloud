package com.doc.cloud.git.service;

import com.doc.cloud.base.vo.InfoVO;

/**
 * Created by majun on 09/02/2018.
 */
public interface GitService {

    /**
     * 创建Git仓库
     * @param repositoryName
     * @return
     */
    InfoVO<String> createRepository(String repositoryName);

}
