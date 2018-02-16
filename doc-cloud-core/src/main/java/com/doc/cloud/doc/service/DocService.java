package com.doc.cloud.doc.service;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.model.Tree;

/**
 * Created by majun on 12/02/2018.
 */
public interface DocService {

    /**
     * 获取文档
     * @param username
     * @param repositoryName
     * @return
     */
    InfoVO<byte[]> getDoc(String username, String repositoryName);

    /**
     * 发布文档
     * @param username
     * @param repositoryName
     * @return
     */
    InfoVO<String> releaseDoc(String username, String repositoryName);

    /**
     * 获取文档目录
     * @param username
     * @param repositoryName
     * @return
     */
    InfoVO<Tree> getDocToc(String username,String repositoryName);

}
