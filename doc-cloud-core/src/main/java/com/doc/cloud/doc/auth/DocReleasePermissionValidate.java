package com.doc.cloud.doc.auth;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.doc.exception.NoPermissionException;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.i18n.constant.I18n;
import org.springframework.stereotype.Service;

/**
 * 发布文档权限验证
 * Created by majun on 20/02/2018.
 */
@Service("docReleasePermissionValidate")
public class DocReleasePermissionValidate extends DocPermissionValidate {

    @Override
    protected void publicRepositoryValidate(Repository repository) throws NoPermissionException {
        //没有权限发布别人的文档
        throw new NoPermissionException(I18nUtils.getValue(I18n.NO_PERMISSION));
    }

}
