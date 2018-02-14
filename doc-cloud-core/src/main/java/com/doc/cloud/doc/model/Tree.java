package com.doc.cloud.doc.model;

import org.apache.shiro.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majun on 13/02/2018.
 */
public class Tree {

    private List<TreeLabel> children;

    public void addLabel(TreeLabel label){
        if(CollectionUtils.isEmpty(children)){
            children = new ArrayList<>();
        }
        children.add(label);
    }

    public List<TreeLabel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeLabel> children) {
        this.children = children;
    }
}
