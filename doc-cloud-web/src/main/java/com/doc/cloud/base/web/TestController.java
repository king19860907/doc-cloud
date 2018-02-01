package com.doc.cloud.base.web;

import com.doc.cloud.base.utils.I18nUtils;
import com.doc.cloud.base.vo.InfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by majun on 30/01/2018.
 */
@Controller
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        return I18nUtils.getValue("test");
    }

    public static void main(String[] args) {
        System.out.println(Locale.forLanguageTag("zh-CN"));
        System.out.println(Locale.getDefault());
    }

}
