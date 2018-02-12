package com.doc.cloud.git.model;

import com.doc.cloud.base.utils.SystemUtils;

/**
 * Created by majun on 12/02/2018.
 */
public class RepositoryPath {

    private String basePath;

    /**
     * 获取裸仓库路径
     * @return
     */
    public String getBarePath(){
        return basePath+ SystemUtils.getFileSeparator()+"bare"+SystemUtils.getFileSeparator()+"{0}"+SystemUtils.getFileSeparator()+"{1}.git";
    }

    /**
     * 获取工作仓库路径
     * @return
     */
    public String getWorkPath(){
        return basePath+ SystemUtils.getFileSeparator()+"work"+SystemUtils.getFileSeparator()+"{0}"+SystemUtils.getFileSeparator()+"{1}";
    }

    /**
     * 获取发布仓库路径
     * @return
     */
    public String getReleasePath(){
        return basePath+ SystemUtils.getFileSeparator()+"release"+SystemUtils.getFileSeparator()+"{0}"+SystemUtils.getFileSeparator()+"{1}";
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
