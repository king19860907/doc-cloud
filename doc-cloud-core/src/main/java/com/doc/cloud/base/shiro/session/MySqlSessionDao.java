package com.doc.cloud.base.shiro.session;

import com.doc.cloud.base.dao.SessionDao;
import com.doc.cloud.base.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by majun on 31/01/2018.
 */
public class MySqlSessionDao extends AbstractSessionDAO {

    @Autowired
    private SessionDao sessionDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        sessionDao.save(sessionId, SerializableUtils.serialize(session));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String sessionStr = sessionDao.getSessionById(sessionId);
        if(!StringUtils.hasText(sessionStr)){
           return null;
        }
        return SerializableUtils.deserialize(sessionStr);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()){
            return;
        }
        sessionDao.updateSession(SerializableUtils.serialize(session),session.getId());
    }

    @Override
    public void delete(Session session) {
        sessionDao.deleteSessionById(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }


}
