package com.doc.cloud.doc.auth;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.doc.exception.NoPermissionException;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.git.util.GitUtils;
import com.doc.cloud.i18n.constant.I18n;
import org.springframework.stereotype.Service;

/**
 * git操作权限认证
 * Created by majun on 20/02/2018.
 */
@Service("gitOperationPermissionValidate")
public class GitOperationPermissionValidate extends DocPermissionValidate {

    @Override
    protected void publicRepositoryValidate(Repository repository) throws NoPermissionException {
        //如果为公有仓库,则有clone权限,但没有push权限
        if(!GitUtils.isClone(RequestUtils.getRequest())){
            throw new NoPermissionException(I18nUtils.getValue(I18n.NO_PERMISSION));
        }
    }

}
