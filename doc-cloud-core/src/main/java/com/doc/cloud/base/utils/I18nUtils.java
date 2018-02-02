package com.doc.cloud.base.utils;

import com.doc.cloud.base.repository.ResourceBundleRepository;
import com.doc.cloud.i18n.constant.I18n;

import javax.servlet.http.Cookie;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by majun on 02/02/2018.
 */
public class I18nUtils {

    public final static String BASE_NAME= "i18n.daocloud";

    private final static String LANG_COOKIE_NAME = "lang";

    public static String getValue(String key){
        String value;
        Cookie cookie = CookieUtils.getCookieByName(RequestUtils.getRequest(),LANG_COOKIE_NAME);
        ResourceBundle resources = ResourceBundleRepository.getResourceBundle(cookie==null?null:cookie.getValue());
        try{
            value = resources.getString(key);
        }catch (MissingResourceException e){
            value = resources.getString(I18n.UN_DEFINED);
        }
        return value;
    }

}
