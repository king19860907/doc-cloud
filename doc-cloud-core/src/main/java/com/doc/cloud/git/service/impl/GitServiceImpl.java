package com.doc.cloud.git.service.impl;

import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.git.pojo.Repository;
import com.doc.cloud.git.service.GitRepository;
import com.doc.cloud.git.service.GitService;
import com.doc.cloud.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by majun on 09/02/2018.
 */
@Service("gitService")
public class GitServiceImpl implements GitService {

    private Logger logger =  LoggerFactory.getLogger(GitServiceImpl.class);

    @Value("#{configurer['base.path']}")
    private String gitPath;

    @Autowired
    private GitRepository gitRepository;

    @Autowired
    private RepositoryDao repositoryDao;

    @Override
    public InfoVO<String> createRepository(String repositoryName) {
        String path = gitPath+"/{0}/{1}.git";
        try{
            User user = RequestUtils.getUser();

            //创建git仓库
            gitRepository.createRepository(MessageFormat.format(path,user.getUsername(),repositoryName),true);

            //生成数据库中记录
            Repository repository = new Repository();
            repository.setName(repositoryName);
            repository.setUserId(user.getUserId());
            repositoryDao.insert(repository);

            return InfoVO.defaultSuccess();
        }catch (Exception e){
            if(logger.isErrorEnabled()){
                logger.error(e.getMessage(),e);
            }
            return InfoVO.defaultError();
        }
    }

}
