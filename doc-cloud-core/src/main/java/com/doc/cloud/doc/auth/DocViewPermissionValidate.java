package com.doc.cloud.doc.auth;

import com.doc.cloud.doc.exception.NoPermissionException;
import com.doc.cloud.git.pojo.Repository;
import org.springframework.stereotype.Service;

/**
 * 查看文档权限验证
 * Created by majun on 20/02/2018.
 */
@Service("docViewPermissionValidate")
public class DocViewPermissionValidate extends DocPermissionValidate {

    @Override
    protected void publicRepositoryValidate(Repository repository) throws NoPermissionException {
        //不做任何操作,公有仓库具有查看权限
    }

}
