package com.doc.cloud.doc.web;

import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * Created by majun on 12/02/2018.
 */
@Controller
@RequestMapping("/doc/view")
public class DocViewController {

    private Logger logger =  LoggerFactory.getLogger(DocViewController.class);

    @Resource
    private DocService docService;

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO docs(@PathVariable("username") String username,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){

        return docService.queryDocsByPage(username,pageNum,pageSize);
    }

    @RequestMapping(value = "/{username}/{repositoryName}/**",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO view(@PathVariable("username") String username, @PathVariable("repositoryName") String repositoryName) throws IOException {
        InfoVO<byte[]> info = docService.getDoc(username,repositoryName);
        ServletOutputStream outputStream = null;
        try{
            if(info.success()){
                byte[] bytes = info.getResult();
                outputStream = RequestUtils.getResponse().getOutputStream();
                outputStream.write(bytes);
            }else{
                return info;
            }
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }finally {
            if(outputStream != null){
                outputStream.close();
            }
        }
        return null;
    }

    @RequestMapping(value = "/toc/{username}/{repositoryName}",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO toc(@PathVariable("username") String username, @PathVariable("repositoryName") String repositoryName){
        return docService.getDocToc(username,repositoryName);
    }

}
