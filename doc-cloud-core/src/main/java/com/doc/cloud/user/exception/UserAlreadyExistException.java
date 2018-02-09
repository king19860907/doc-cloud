package com.doc.cloud.user.exception;

import com.doc.cloud.base.exception.UserException;

/**
 * Created by majun on 09/02/2018.
 */
public class UserAlreadyExistException extends UserException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

}
