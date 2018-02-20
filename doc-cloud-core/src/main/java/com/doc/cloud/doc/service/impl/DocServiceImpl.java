package com.doc.cloud.doc.service.impl;

import com.doc.cloud.base.utils.MediaTypeUtils;
import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.utils.SystemUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.doc.auth.DocPermissionValidate;
import com.doc.cloud.doc.exception.NoPermissionException;
import com.doc.cloud.doc.model.Doc;
import com.doc.cloud.doc.model.Tree;
import com.doc.cloud.doc.model.TreeLabel;
import com.doc.cloud.doc.service.DocService;
import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.git.model.RepositoryPath;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.git.service.GitRepository;
import com.doc.cloud.user.dao.UserDao;
import com.doc.cloud.user.pojo.User;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private DocPermissionValidate docViewPermissionValidate;

    @Autowired
    private DocPermissionValidate docReleasePermissionValidate;

    @Override
    public InfoVO<byte[]> getDoc(String username, String repositoryName) {
        try {

            docViewPermissionValidate.validate(username,repositoryName);

            HttpServletResponse response = RequestUtils.getResponse();
            HttpServletRequest request = RequestUtils.getRequest();

            String workPath = MessageFormat.format(repositoryPath.getReleasePath(),username,repositoryName);
            String docPath = getDocPath();
            String filePath = workPath+ SystemUtils.getFileSeparator()+docPath;

            String suffix = docPath.substring(docPath.lastIndexOf(".")+1,docPath.length());
            byte[] bytes = null;
            File file = new File(filePath);
            if(file.exists()) {
                bytes = Files.readAllBytes(Paths.get(filePath));
                if(MediaTypeUtils.isMD(suffix)){
                    String url = request.getRequestURL().toString().replaceAll(docPath,"images");
                    String content = new String(bytes);
                    content = replaceImagePath(content,url);
                    bytes = content.getBytes();
                }
            }
            response.setHeader("accept-ranges","bytes");
            response.setContentType(MediaTypeUtils.getMediaType(suffix));
            return InfoVO.defaultSuccess(bytes);
        } catch (NoPermissionException e1){
            if(logger.isWarnEnabled()){
                String message = e1.getMessage()+" LoginUser:{0} username:{1} RepositoryName:{2}";
                logger.warn(MessageFormat.format(message,RequestUtils.getUser().getUsername(),username,repositoryName));
            }
            return InfoVO.noPermission();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }
    }

    @Override
    public InfoVO<String> releaseDoc(String username, String repositoryName) {
        String releasePath = MessageFormat.format(repositoryPath.getReleasePath(),username,repositoryName);
        try{
            docReleasePermissionValidate.validate(username,repositoryName);
            gitRepository.pull(releasePath);
            return InfoVO.defaultSuccess();
        }catch (NoPermissionException e1){
            if(logger.isWarnEnabled()){
                String message = e1.getMessage()+" LoginUser:{0} username:{1} RepositoryName:{2}";
                logger.warn(MessageFormat.format(message,RequestUtils.getUser().getUsername(),username,repositoryName));
            }
            return InfoVO.noPermission();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }
    }

    @Override
    public InfoVO<Tree> getDocToc(String username, String repositoryName) {
        try{
            Repository repository = docViewPermissionValidate.validate(username,repositoryName);

            String tocPath = MessageFormat.format(repositoryPath.getTocPath(),username,repositoryName);
            byte[] bytes = Files.readAllBytes(Paths.get(tocPath));
            Tree tree = processContent(new String(bytes));
            return InfoVO.defaultSuccess(new Doc(tree,repository));
        } catch (NoPermissionException e1){
            if(logger.isWarnEnabled()){
                String message = e1.getMessage()+" LoginUser:{0} username:{1} RepositoryName:{2}";
                logger.warn(MessageFormat.format(message,RequestUtils.getUser().getUsername(),username,repositoryName));
            }
            return InfoVO.noPermission();
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            return InfoVO.defaultError();
        }
    }

    private Tree processContent(String content){
        if(StringUtils.hasText(content)){
            String[] lines = content.split("\n");
            Tree tree = new Tree();
            List<TreeLabel> allLabels = new ArrayList<>();
            for(int i=0;i<lines.length;i++){
                String line = lines[i];
                System.out.println(line+":"+line.indexOf("*"));
                TreeLabel label = new TreeLabel(line);
                allLabels.add(label);
                if(label.getIndex() == 0){
                    tree.addLabel(label);
                }else{
                    processLabel(label,allLabels.get(i-1));
                }
            }
            return tree;
        }
        return null;
    }

    public void processLabel(TreeLabel currentLabel, TreeLabel lastLabel){
        if(currentLabel.getIndex()>lastLabel.getIndex()){
            lastLabel.addLabel(currentLabel);
            currentLabel.setParent(lastLabel);
        }else{
            TreeLabel parent = findParent(currentLabel,lastLabel);
            parent.addLabel(currentLabel);
            currentLabel.setParent(parent);
        }
    }

    public TreeLabel findParent(TreeLabel currentLabel,TreeLabel lastLabel){
        if(lastLabel.getIndex() == currentLabel.getIndex()){
            return lastLabel.getParent();
        }
        return findParent(currentLabel,lastLabel.getParent());
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
