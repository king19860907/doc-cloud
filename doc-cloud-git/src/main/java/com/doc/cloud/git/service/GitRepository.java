package com.doc.cloud.git.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

/**
 * Created by majun on 09/02/2018.
 */
public class GitRepository {

    /**
     * 创建空仓库
     */
    public void createRepository(String path,boolean bare) throws GitAPIException {
        Git.init().setBare(bare).setDirectory(new File(path)).call();
    }

    /**
     * clone 仓库
     * @param remotePath    远程地址
     * @param workPath      本地地址
     * @throws GitAPIException
     */
    public void clone(String remotePath,String workPath) throws GitAPIException {
        Git.cloneRepository().setURI(remotePath).setDirectory(new File(workPath)).call();
    }

}
