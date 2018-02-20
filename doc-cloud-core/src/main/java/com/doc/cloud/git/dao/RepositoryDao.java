package com.doc.cloud.git.dao;

import com.doc.cloud.git.pojo.Repository;
import org.apache.ibatis.annotations.Param;

/**
 * Created by majun on 10/02/2018.
 */
@org.springframework.stereotype.Repository
public interface RepositoryDao {

    int insert(@Param("repository") Repository repository);

    /**
     * 根据用户ID和仓库名获取仓库
     * @param userId  用户ID
     * @param repositoryName    仓库名称
     * @return
     */
    Repository getRepositoryByUserIdAndName(@Param("userId") Long userId, @Param("name") String repositoryName);

    /**
     * 根据用户名仓库名获取仓库
     * @param username
     * @param repositoryName
     * @return
     */
    Repository getRepositoryByUserNameAndName(@Param("username") String username, @Param("repositoryName") String repositoryName);

}
