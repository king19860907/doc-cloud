package com.doc.cloud.base.vo;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.i18n.constant.I18n;

/**
 * Created by majun on 31/01/2018.
 */
public class InfoVO<T> {

    private String msg;

    private Integer flag;

    private T result;

    public final static Integer SUCCESS = 1;

    public final static Integer ERROR = 0;

    public final static Integer NOT_LOGIN = -1;

    public final static Integer NO_PERMISSION = -2;

    public static <String>InfoVO notLogin(){
        return defaultError(NOT_LOGIN, I18nUtils.getValue(I18n.NOT_LOGIN));
    }

    public static <String>InfoVO noPermission(){
        return defaultError(NO_PERMISSION,I18nUtils.getValue(I18n.NO_PERMISSION));
    }

    public static <T>InfoVO defaultSuccess(){
        return defaultSuccess(null);
    }

    public static <T>InfoVO defaultSuccess(T result){
        InfoVO info = null;
        info = new InfoVO(InfoVO.SUCCESS, I18nUtils.getValue(I18n.SUCCESS),result);
        return info;
    }

    public static<T>InfoVO defaultError(){
        return defaultError(I18nUtils.getValue(I18n.SYSTEM_ERROR));
    }

    public static<T>InfoVO defaultError(String errorMsg){
        return defaultError(InfoVO.ERROR,errorMsg);
    }

    public static<T>InfoVO defaultError(Integer errorCode,String errorMsg){
        InfoVO info = null;
        info = new InfoVO(errorCode, errorMsg);
        return info;
    }

    public static<T>InfoVO error(String message){
        return new InfoVO(InfoVO.ERROR,message);
    }

    public InfoVO(Integer flag, String msg, T result) {
        this.flag = flag;
        this.msg = msg;
        this.result = result;
    }

    public InfoVO(Integer flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean success(){
        return this.flag.intValue() == InfoVO.SUCCESS.intValue();
    }

}
