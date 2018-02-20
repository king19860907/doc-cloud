package com.doc.cloud.git.model;


import com.doc.cloud.git.util.SystemUtils;

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
        return getBareFolder()+SystemUtils.getFileSeparator()+"{0}"+SystemUtils.getFileSeparator()+"{1}.git";
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

    /**
     * 获取目录路径
     * @return
     */
    public String getTocPath(){
        return getReleasePath()+SystemUtils.getFileSeparator()+"SUMMARY.md";
    }

    /**
     * 获取裸仓库文件夹路径
     * @return
     */
    public String getBareFolder(){
        return basePath + SystemUtils.getFileSeparator()+"bare";
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
