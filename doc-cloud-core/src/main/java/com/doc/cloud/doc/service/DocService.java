package com.doc.cloud.doc.service;

import com.doc.cloud.base.vo.InfoVO;

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

}
