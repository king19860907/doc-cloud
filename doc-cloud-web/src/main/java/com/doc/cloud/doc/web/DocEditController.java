package com.doc.cloud.doc.web;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by majun on 12/02/2018.
 */
@Controller
public class DocEditController {

    @Resource
    private DocService docService;

    @RequestMapping(value = "/doc/release/{username}/{docName}",method = RequestMethod.POST)
    @ResponseBody
    public InfoVO<String> releaseDoc(@PathVariable("username") String username, @PathVariable("docName") String docName){
        return docService.releaseDoc(username,docName);
    }

}
