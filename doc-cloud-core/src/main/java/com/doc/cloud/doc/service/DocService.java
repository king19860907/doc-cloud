package com.doc.cloud.doc.service;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.model.Tree;
import com.doc.cloud.git.pojo.Repository;
import com.github.pagehelper.PageInfo;

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

    /**
     * 根据用户名获取文档列表
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     */
    InfoVO<PageInfo<Repository>> queryDocsByPage(String username, int pageNum, int pageSize);

}
