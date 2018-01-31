package com.doc.cloud.base.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
/**
 * Created by majun on 31/01/2018.
 */
@Repository
public interface SessionDao {

    void save(@Param("sessionId") Serializable sessionId, @Param("session") String session);

    String getSessionById(@Param("sessionId") Serializable sessionId);

    void updateSession(@Param("session") String session, @Param("sessionId") Serializable sessionId);

    void deleteSessionById(@Param("sessionId") Serializable sessionId);

    List<String> getActiveSessions(@Param("globalSessionTimeout") Long globalSessionTimeout);

}
