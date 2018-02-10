package com.doc.cloud.base.shiro.filter;

import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.user.dao.UserDao;
import com.doc.cloud.user.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by majun on 10/02/2018.
 */
public class GitBasicHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    private RepositoryDao repositoryDao;

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        validateAuth(token,request);
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
        //return onLoginFailure(token,null,request,response);
    }

    /**
     * TODO
     * 验证git权限
     * 1.私有项目
     * 私有项目只能用户自己clone或push代码
     * 2.公有项目
     * 只能push自己项目的代码
     * @param token
     * @param request
     */

    private void validateAuth(AuthenticationToken token,ServletRequest request){
        //throw new AuthenticationException("no authentication");
    }

}
