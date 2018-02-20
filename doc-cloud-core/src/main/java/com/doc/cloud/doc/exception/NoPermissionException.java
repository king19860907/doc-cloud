package com.doc.cloud.doc.exception;

import com.doc.cloud.base.exception.UserException;

/**
 * Created by majun on 20/02/2018.
 */
public class NoPermissionException extends UserException {

    public NoPermissionException(String message) {
        super(message);
    }

}
