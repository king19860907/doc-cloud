package com.doc.cloud.base.web;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * Created by majun on 30/01/2018.
 */
@Controller
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO test(){
        return InfoVO.defaultSuccess(RequestUtils.getUser());
    }

    public static void main(String[] args) {
        System.out.println(Locale.forLanguageTag("zh-CN"));
        System.out.println(Locale.getDefault());
    }

}
