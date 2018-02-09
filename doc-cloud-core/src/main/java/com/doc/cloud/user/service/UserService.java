package com.doc.cloud.user.service;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.user.pojo.User;

/**
 * Created by majun on 09/02/2018.
 */
public interface UserService {

    /**
     * 创建用户
     * @param username
     * @param password
     * @param email
     * @return
     */
    InfoVO<User> createUser(String username,String password,String email);

}
