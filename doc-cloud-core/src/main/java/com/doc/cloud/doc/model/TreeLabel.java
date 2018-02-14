package com.doc.cloud.doc.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by majun on 13/02/2018.
 */
public class TreeLabel {

    private String name;

    private String href;

    @JSONField(serialize=false)
    @JsonIgnore
    private transient String content;

    private List<TreeLabel> children;

    @JSONField(serialize=false)
    @JsonIgnore
    private transient TreeLabel parent;

    @JSONField(serialize=false)
    @JsonIgnore
    private transient int index;

    public void addLabel(TreeLabel label){
        if(CollectionUtils.isEmpty(children)){
            children = new ArrayList<>();
        }
        children.add(label);
    }

    public TreeLabel(String content){
        if(StringUtils.hasText(content)){
            this.index = content.indexOf("*");
            this.content = content.replaceAll(" ","");
            processName(this.content);
            processHref(this.content);
        }
    }

    public void processName(String content){
        String regex = "\\*\\[.*\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            this.name = matcher.group().replaceAll("\\*\\[","").replaceAll("\\]","");
        }
    }

    public void processHref(String content){
        String regex = "\\(.*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            this.href = matcher.group().replaceAll("\\(","").replaceAll("\\)","");
        }
    }

    public List<TreeLabel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeLabel> children) {
        this.children = children;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    public TreeLabel getParent() {
        return parent;
    }

    public void setParent(TreeLabel parent) {
        this.parent = parent;
    }

    @Transient
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
