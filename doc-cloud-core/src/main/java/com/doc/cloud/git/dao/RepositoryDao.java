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
     * 获取仓库
     * @param userId  用户ID
     * @param name    仓库名称
     * @return
     */
    Repository getRepositoryByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

}
