package com.doc.cloud.doc.service.impl;

import com.doc.cloud.base.utils.MediaTypeUtils;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.utils.SystemUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.service.DocService;
import com.doc.cloud.git.model.RepositoryPath;
import com.doc.cloud.git.service.GitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by majun on 12/02/2018.
 */
@Service("docService")
public class DocServiceImpl implements DocService {

    private Logger logger =  LoggerFactory.getLogger(DocServiceImpl.class);

    @Autowired
    private RepositoryPath repositoryPath;

    @Autowired
    private GitRepository gitRepository;

    @Override
    public InfoVO<byte[]> getDoc(String username, String docName) {
        HttpServletResponse response = RequestUtils.getResponse();
        HttpServletRequest request = RequestUtils.getRequest();

        String workPath = MessageFormat.format(repositoryPath.getReleasePath(),username,docName);
        String docPath = getDocPath();
        String filePath = workPath+ SystemUtils.getFileSeparator()+docPath;
        try {
            String suffix = docPath.substring(docPath.lastIndexOf(".")+1,docPath.length());
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            if(MediaTypeUtils.isMD(suffix)){
                String url = request.getRequestURL().toString().replaceAll(docPath,"images");
                String content = new String(bytes);
                content = replaceImagePath(content,url);
                bytes = content.getBytes();
            }
            response.setHeader("accept-ranges","bytes");
            response.setContentType(MediaTypeUtils.getMediaType(suffix));
            return InfoVO.defaultSuccess(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }
    }

    @Override
    public InfoVO<String> releaseDoc(String username, String docName) {
        String releasePath = MessageFormat.format(repositoryPath.getReleasePath(),username,docName);
        try{
            gitRepository.pull(releasePath);
            return InfoVO.defaultSuccess();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }
    }

    private String getDocPath(){
        HttpServletRequest request = RequestUtils.getRequest();
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String docPath = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
        docPath = docPath.replaceAll("/",SystemUtils.getFileSeparator());
        return docPath;
    }

    /**
     * 将markdown中图片引用相对路径
     * ![test](/images/test.jpg) 替换为
     * ![test](http://localhost:8080/images/test.jpg) 绝对http路径
     * @param content
     * @param replaceStr
     * @return
     */
    private String replaceImagePath(String content,String replaceStr){
        String regex = "!.*\\)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            matcher.reset();
            StringBuffer sb = new StringBuffer();
            while(matcher.find()){
                String str = matcher.group();
                //替换后的字符串
                String replacedStr = str;
                if(!str.contains("http://")){
                    replacedStr = str.replaceAll("/images",replaceStr);
                }
                matcher.appendReplacement(sb,replacedStr);
            }
            return sb.toString();
        }
        return content;
    }

}
