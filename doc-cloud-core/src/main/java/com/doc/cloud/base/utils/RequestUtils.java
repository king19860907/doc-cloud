package com.doc.cloud.base.utils;

import com.doc.cloud.base.filter.GetRequestAndResponseFilter;
import com.doc.cloud.user.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by majun on 31/01/2018.
 */
public class RequestUtils {

    public static final String SESSION_USER = "SESSION_USER";

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return getSubject().getSession();
    }

    public static User getUser(){
        return (User)getSession().getAttribute(SESSION_USER);
    }

    public static HttpServletRequest getRequest(){
        return GetRequestAndResponseFilter.getCurrentRequest();
    }

    public static HttpServletResponse getResponse(){
        return GetRequestAndResponseFilter.getCurrentResponse();
    }

}
