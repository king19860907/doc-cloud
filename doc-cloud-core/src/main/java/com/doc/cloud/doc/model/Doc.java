package com.doc.cloud.doc.model;

import com.doc.cloud.git.pojo.Repository;

/**
 * Created by majun on 16/02/2018.
 */
public class Doc {

    private Tree leftMenu;

    private String docName;

    private Long repositoryId;

    private String repositoryName;

    public Doc(){}

    public Doc(Tree tree, Repository repository){
        this.leftMenu = tree;
        this.docName = repository.getDisplayName();
        this.repositoryId = repository.getId();
        this.repositoryName = repository.getName();
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Tree getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(Tree leftMenu) {
        this.leftMenu = leftMenu;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
