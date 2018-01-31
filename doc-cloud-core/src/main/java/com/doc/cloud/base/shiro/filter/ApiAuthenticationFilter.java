package com.doc.cloud.base.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.user.dao.UserDao;
import com.doc.cloud.user.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口登录权限验证
 * Created by majun on 31/01/2018.
 */
public class ApiAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiAuthenticationFilter.class);

    @Autowired
    private UserDao userDao;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果是登录请求,默认为不能访问
        if(isLoginRequest(request,response)){
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(isLoginRequest(request,response)){
            return executeLogin(request,response);
        }else{
            outResponse(response, JSONObject.toJSON(InfoVO.notLogin()));
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        User user = userDao.getUserByUsername(token.getPrincipal().toString());
        subject.getSession().setAttribute(RequestUtils.SESSION_USER,user);
        outResponse(response,JSONObject.toJSON(InfoVO.defaultSuccess("success")));
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        outResponse(response,InfoVO.defaultError("用户名密码错误"));
        return false;
    }

    private void outResponse(ServletResponse response,Object o){
        HttpServletResponse res = (HttpServletResponse)response;
        outResponse(res,o);
    }

    private void outResponse(HttpServletResponse response,Object o){
        try{
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(JSONObject.toJSON(o));
        }catch (IOException e1) {
            log.error(e1.getMessage(),e1);
        }
    }

}
