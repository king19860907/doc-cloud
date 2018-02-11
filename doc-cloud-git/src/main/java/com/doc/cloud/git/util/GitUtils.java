package com.doc.cloud.git.util;

import org.eclipse.jgit.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by majun on 11/02/2018.
 */
public class GitUtils {

    private static final String GIT_COMMAND_PARAMETER = "service";

    public static boolean isPush(HttpServletRequest request){
        return matchCommand(request,"git-receive-pack");
    }

    public static boolean isClone(HttpServletRequest request){
        return matchCommand(request,"git-upload-pack");
    }

    private static boolean matchCommand(HttpServletRequest request,String serviceName){
        String service = request.getParameter(GIT_COMMAND_PARAMETER);
        if(serviceName.equals(service) || request.getRequestURI().contains(serviceName)){
            return true;
        }
        return false;
    }

}
