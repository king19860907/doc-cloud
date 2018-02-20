package com.doc.cloud.git.web.servlet;

import com.doc.cloud.git.model.RepositoryPath;
import org.eclipse.jgit.http.server.GitFilter;
import org.eclipse.jgit.http.server.GitServlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by majun on 09/02/2018.
 */
public class DocCloudGitServlet extends GitServlet {

    private final GitFilter gitFilter;

    public DocCloudGitServlet() {
        super();
        gitFilter = (GitFilter) getDelegateFilter();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {
        gitFilter.init(new FilterConfig() {
            @Override
            public String getFilterName() {
                return gitFilter.getClass().getName();
            }

            @Override
            public String getInitParameter(String name) {
                if(name.equals("base-path")){
                    return getBasePath(config.getInitParameter(name));
                }
                return config.getInitParameter(name);
            }

            @Override
            public Enumeration<String> getInitParameterNames() {
                return config.getInitParameterNames();
            }

            @Override
            public ServletContext getServletContext() {
                return config.getServletContext();
            }
        });
    }

    private String getBasePath(String basePath){
        Properties properties =  new Properties();
        InputStream in;
        try{
            in = this.getClass().getClassLoader().getResourceAsStream(basePath);
            properties.load(in);
            String path = String.valueOf(properties.get("base.path"));
            RepositoryPath repositoryPath = new RepositoryPath();
            repositoryPath.setBasePath(path);
            return repositoryPath.getBareFolder();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
