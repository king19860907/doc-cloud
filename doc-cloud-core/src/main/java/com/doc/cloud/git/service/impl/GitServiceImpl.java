package com.doc.cloud.git.service.impl;

import com.doc.cloud.base.utils.RequestUtils;
import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.git.dao.RepositoryDao;
import com.doc.cloud.git.model.RepositoryPath;
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

    @Autowired
    private RepositoryPath repositoryPath;

    @Autowired
    private GitRepository gitRepository;

    @Autowired
    private RepositoryDao repositoryDao;

    @Override
    public InfoVO<String> createRepository(String repositoryName) {
        User user = RequestUtils.getUser();
        String barePath = MessageFormat.format(repositoryPath.getBarePath(),user.getUsername(),repositoryName);
        String workPath = MessageFormat.format(repositoryPath.getWorkPath(),user.getUsername(),repositoryName);
        String releasePath = MessageFormat.format(repositoryPath.getReleasePath(),user.getUsername(),repositoryName);
        try{
            //创建git仓库
            gitRepository.createRepository(barePath,true);

            //clone工作目录
            gitRepository.clone(barePath,workPath);

            //clone发布目录
            gitRepository.clone(barePath,releasePath);

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
