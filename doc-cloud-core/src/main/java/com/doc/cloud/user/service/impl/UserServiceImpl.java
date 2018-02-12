package com.doc.cloud.user.service.impl;

import com.doc.cloud.base.exception.UserException;
import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.i18n.constant.I18n;
import com.doc.cloud.user.dao.UserDao;
import com.doc.cloud.user.exception.EmailAlreadyExistException;
import com.doc.cloud.user.exception.UserAlreadyExistException;
import com.doc.cloud.user.pojo.User;
import com.doc.cloud.user.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by majun on 09/02/2018.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private HashedCredentialsMatcher credentialsMatcher;

    @Override
    public InfoVO<User> createUser(String username, String password, String email) {
        try{

            validateUserNotExist(username);
            validateEmailNotExist(email);

            SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
            String salt = randomNumberGenerator.nextBytes().toHex();

            User user = new User();
            user.setUsername(username);
            user.setPassword(generatePassword(username,password,salt));
            user.setEmail(email);
            user.setPasswordSalt(salt);

            userDao.insert(user);

            user = userDao.getUserByUsername(username);

            return InfoVO.defaultSuccess(user);
        }catch(UserException e1){
            if(logger.isWarnEnabled()){
                logger.warn(e1.getMessage());
            }
            return InfoVO.defaultError(e1.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }

    }

    /**
     * 验证用户不存在,存在则抛出异常
     * @param username
     */
    private void validateUserNotExist(String username) throws UserAlreadyExistException {
        User user = userDao.getUserByUsername(username);
        if(user != null){
            throw new UserAlreadyExistException(I18nUtils.getValue(I18n.User.USER_ALREADY_EXIST));
        }
    }

    /**
     * 验证email不存在
     * @param email
     */
    private void validateEmailNotExist(String email) throws EmailAlreadyExistException {
        User user = userDao.getUserByEmail(email);
        if(user != null){
            throw new EmailAlreadyExistException(I18nUtils.getValue(I18n.User.EMAIL_ALREADY_EXIST));
        }
    }

    /**
     * 生成密码
     * @param username
     * @param password
     * @param salt
     * @return
     */
    private String generatePassword(String username,String password,String salt){
        String algorithmName = credentialsMatcher.getHashAlgorithmName();
        int iterations = credentialsMatcher.getHashIterations();
        SimpleHash hash = new SimpleHash(algorithmName,password,username+salt,iterations);
        String encodePassword=hash.toHex();
        return encodePassword;
    }

}
