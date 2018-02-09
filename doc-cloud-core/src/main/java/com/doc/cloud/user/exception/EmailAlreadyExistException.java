package com.doc.cloud.user.exception;

import com.doc.cloud.base.exception.UserException;

/**
 * Created by majun on 09/02/2018.
 */
public class EmailAlreadyExistException extends UserException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
