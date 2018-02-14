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
     * @param docName
     * @return
     */
    InfoVO<byte[]> getDoc(String username, String docName);

    /**
     * 发布文档
     * @param username
     * @param docName
     * @return
     */
    InfoVO<String> releaseDoc(String username, String docName);

    /**
     * 获取文档目录
     * @param username
     * @param docName
     * @return
     */
    InfoVO<Tree> getDocToc(String username,String docName);

}
