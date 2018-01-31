package com.doc.cloud.user.dao;

import com.doc.cloud.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by majun on 31/01/2018.
 */
@Repository
public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User getUserByUsername(@Param("username") String username);

}
