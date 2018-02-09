package com.doc.cloud.base.shiro.session;

import com.doc.cloud.base.dao.SessionDao;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.utils.SerializableUtils;
import com.doc.cloud.user.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        Long userId = null;
        User user = (User)session.getAttribute(RequestUtils.SESSION_USER);
        if(user != null){
            userId = user.getUserId();
        }
        sessionDao.updateSession(SerializableUtils.serialize(session),session.getId(),userId);
    }

    @Override
    public void delete(Session session) {
        sessionDao.deleteSessionById(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Long globalSessionTimeout = null;
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        if(securityManager instanceof SessionsSecurityManager){
            SessionsSecurityManager sessionsSecurityManager = (SessionsSecurityManager)securityManager;
            SessionManager sessionManager = sessionsSecurityManager.getSessionManager();
            if(sessionManager instanceof DefaultSessionManager){
                DefaultSessionManager defaultSessionManager = (DefaultSessionManager)sessionManager;
                globalSessionTimeout = defaultSessionManager.getGlobalSessionTimeout();
            }
        }
        List<String> sessionList = sessionDao.getActiveSessions(globalSessionTimeout);
        List<Session> sessions = sessionList.stream().map(sessionStr->SerializableUtils.deserialize(sessionStr))
                .collect(Collectors.toList());
        return sessions;
    }


}
