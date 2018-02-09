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

}
