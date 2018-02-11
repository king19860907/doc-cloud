package com.doc.cloud.git;

import org.apache.shiro.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by majun on 10/02/2018.
 */
@SuppressWarnings("ALL")
public class RepositoryTest {

    @Test
    public void getRepositoryNameTest(){
        Assert.assertNull(getRepositoryName(null));
        Assert.assertNull(getRepositoryName(""));
        Assert.assertEquals("test",getRepositoryName("repos/majun/test.git/info/refs"));
        Assert.assertEquals("test_1",getRepositoryName("repos/majun/test_1.git/info/refs"));
        Assert.assertEquals("test-1",getRepositoryName("repos/majun/test-1.git/info/refs"));
        Assert.assertEquals("test-10",getRepositoryName("repos/majun/test-10.git/info/refs"));
        Assert.assertEquals("test-1_0",getRepositoryName("repos/majun/test-1_0.git/info/refs"));
        Assert.assertEquals("test-1_(0)",getRepositoryName("repos/majun/test-1_(0).git/info/refs"));
        Assert.assertEquals("test-1_(0)@163.com",getRepositoryName("repos/majun/test-1_(0)@163.com.git/info/refs"));

        Assert.assertEquals("majun",getRepositoryUser("/repos/majun/test.git/info/refs"));
        Assert.assertEquals("majun-1",getRepositoryUser("repos/majun-1/test.git/info/refs"));
        Assert.assertEquals("majun_1",getRepositoryUser("repos/majun_1/test.git/info/refs"));
        Assert.assertEquals("majun@1",getRepositoryUser("repos/majun@1/test.git/info/refs"));
        Assert.assertEquals("majun@1.com",getRepositoryUser("repos/majun@1.com/test.git/info/refs"));
    }

    private String getRepositoryName(String uri) {
        if(!StringUtils.hasText(uri)){
            return null;
        }
        String regex = "[a-zA-Z-_0-9()@.]+.git";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        if(matcher.find()){
            String repositoryName = matcher.group();
            return repositoryName.replaceAll(".git","");
        }
        return null;
    }

    private String getRepositoryUser(String uri) {
        if(!StringUtils.hasText(uri)){
            return null;
        }
        String regex = "[a-zA-Z-_0-9()@.]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        int i = 0;
        while (matcher.find()){
            if(i==1){
                return matcher.group();
            }
            i++;
        }
        return null;
    }

}
