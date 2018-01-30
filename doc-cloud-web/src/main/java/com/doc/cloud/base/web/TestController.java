package com.doc.cloud.base.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by majun on 30/01/2018.
 */
@Controller
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> test(){
        Map<String,String> map = new HashMap<>();
        map.put("aaa","bbb");
        return map;
    }

}
