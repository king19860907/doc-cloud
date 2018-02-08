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

    /**
     * 获取国际化资源内容
     * 1.从cookie中获取语言
     * 2.cookie未定义则获取系统语言
     * 3.系统语言未定义properties资源文件,则获取默认资源文件
     * @param key
     * @return
     */
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
