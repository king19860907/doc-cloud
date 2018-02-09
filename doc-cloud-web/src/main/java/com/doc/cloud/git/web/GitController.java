package com.doc.cloud.git.web;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.git.dto.req.CreateRepositoryReq;
import com.doc.cloud.git.service.GitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by majun on 09/02/2018.
 */
@Controller
public class GitController {

    @Resource
    private GitService gitService;

    @RequestMapping(value = "/git/repositories",method = RequestMethod.POST)
    @ResponseBody
    public InfoVO<String> createRepository(@RequestBody CreateRepositoryReq req){
        return gitService.createRepository(req.getName());
    }

}
