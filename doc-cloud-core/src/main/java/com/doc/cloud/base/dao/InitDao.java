package com.doc.cloud.base.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by majun on 10/02/2018.
 */
@Repository
public interface InitDao {

    void createDatabase();

    void insertInitData();

}
