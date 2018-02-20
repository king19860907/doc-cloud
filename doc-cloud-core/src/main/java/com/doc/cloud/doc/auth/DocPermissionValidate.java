package com.doc.cloud.doc.auth;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.doc.exception.NoPermissionException;
import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.i18n.constant.I18n;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by majun on 20/02/2018.
 */
public abstract class DocPermissionValidate {

    @Autowired
    private RepositoryDao repositoryDao;

    public Repository validate(String repositoryUsername,String repositoryName) throws NoPermissionException {
        return validate(RequestUtils.getUser().getUsername(),repositoryUsername,repositoryName);
    }

    public Repository validate(String loginUserName,String repositoryUsername, String repositoryName) throws NoPermissionException {
        Repository repository = repositoryDao.getRepositoryByUserNameAndName(repositoryUsername,repositoryName);
        //如果当前登录人和操作的文档所有人不是同一人
        if(!repositoryUsername.equals(loginUserName)){
            //如果为私有仓库
            if(repository.isPrivate()){
                privateRepositoryValidate(repository);
            }else{
                //如果为公有仓库
                publicRepositoryValidate(repository);
            }
        }
        return repository;
    }

    protected void privateRepositoryValidate(Repository repository) throws NoPermissionException {
        throw new NoPermissionException(I18nUtils.getValue(I18n.NO_PERMISSION));
    }

    abstract protected void publicRepositoryValidate(Repository repository) throws NoPermissionException;

}
