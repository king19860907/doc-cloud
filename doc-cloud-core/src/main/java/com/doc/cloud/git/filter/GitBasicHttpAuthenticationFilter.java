package com.doc.cloud.git.filter;

import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.git.util.GitUtils;
import com.doc.cloud.user.dao.UserDao;
import com.doc.cloud.user.pojo.User;
import com.doc.cloud.user.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by majun on 10/02/2018.
 */
public class GitBasicHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RepositoryDao repositoryDao;

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            validateAuth(token,request);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 验证git权限
     * 1.私有项目
     * 私有项目只能用户自己clone或push代码
     * 2.公有项目
     * 只能push自己项目的代码
     * @param token
     * @param request
     */
    private void validateAuth(AuthenticationToken token,ServletRequest request){
        HttpServletRequest req = (HttpServletRequest)request;
        String loginUserName = token.getPrincipal().toString();
        String repositoryUserName = getRepositoryUser(req.getRequestURI());

        //如果验证用户和需要操作的git用户不是同一人
        if(!loginUserName.equals(repositoryUserName)){
            String repositoryName = getRepositoryName(req.getRequestURI());
            User repositoryUser = userDao.getUserByUsername(repositoryUserName);
            Repository repository = repositoryDao.getRepositoryByUserIdAndName(repositoryUser.getUserId(),repositoryName);
            //如果为私有仓库,则没有权限
            if(repository.isPrivate()){
                throw new AuthenticationException("no authentication");
            }
            //如果为公有仓库,则有clone权限,但没有push权限
            if(!GitUtils.isClone(req)){
                throw new AuthenticationException("no authentication");
            }
        }
    }

    private String getRepositoryName(String uri) {
        if(!StringUtils.hasText(uri)){
            return null;
        }
        String regex = "[a-zA-Z-_0-9()@.]+.git";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        if(matcher.find()){
            String repositoryName = matcher.group();
            return repositoryName.replaceAll(".git","");
        }
        return null;
    }

    private String getRepositoryUser(String uri) {
        if(!StringUtils.hasText(uri)){
            return null;
        }
        String regex = "[a-zA-Z-_0-9()@.]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        int i = 0;
        while (matcher.find()){
            if(i==1){
                return matcher.group();
            }
            i++;
        }
        return null;
    }

}
