package com.doc.cloud.doc.web;

import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/{username}/{docName}/**",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO view(@PathVariable("username") String username, @PathVariable("docName") String docName) throws IOException {
        InfoVO<byte[]> info = docService.getDoc(username,docName);
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

    @RequestMapping(value = "/toc/{username}/{docName}",method = RequestMethod.GET)
    @ResponseBody
    public InfoVO toc(@PathVariable("username") String username, @PathVariable("docName") String docName){
        return docService.getDocToc(username,docName);
    }

}
