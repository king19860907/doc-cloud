package com.doc.cloud.git.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

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

    /**
     * pull仓库
     * @param workPath
     * @throws IOException
     */
    public void pull(String workPath) throws IOException, GitAPIException {
        Git.open(new File(workPath)).pull().call();
    }

    /**
     * add 操作
     * @param workPath
     * @throws IOException
     * @throws GitAPIException
     */
    public void add(String workPath) throws IOException, GitAPIException {
        Git.open(new File(workPath)).add().addFilepattern(".").call();
    }

    /**
     * commit操作
     * @param workPath
     * @param message
     * @throws IOException
     * @throws GitAPIException
     */
    public void commit(String workPath,String message) throws IOException, GitAPIException {
        Git.open(new File(workPath)).commit().setAll(true).setMessage(message).call();
    }

    /**
     * add 和 commit操作
     * @param workPath
     * @param message
     * @throws IOException
     * @throws GitAPIException
     */
    public void addAndCommit(String workPath,String message) throws IOException, GitAPIException {
        add(workPath);
        commit(workPath,message);
    }

    /**
     * push 操作
     * @param workPath
     * @throws IOException
     * @throws GitAPIException
     */
    public void push(String workPath) throws IOException, GitAPIException {
        Git.open(new File(workPath)).push().call();
    }

}
