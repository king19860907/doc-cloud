package com.doc.cloud.base.repository;

import com.doc.cloud.base.utils.I18nUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by majun on 02/02/2018.
 */
@Service
public class ResourceBundleRepository implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ResourceBundleRepository.class);

    public static Map<String,ResourceBundle> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(log.isInfoEnabled()){
            log.info("----开始加载国际化文件-----");
        }
        map = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource("i18n").getFile();
        if(StringUtils.hasText(filePath)){
            String jarFilePath = filePath.substring(0,filePath.indexOf("!")).replace("file:","");
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                String fileName = jarEntry.getName();
                if(fileName.contains(".properties") && fileName.contains("_")){
                    String lang = fileName.substring(fileName.indexOf("_")+1,fileName.indexOf(".properties"));
                    map.put(lang.replace("_","-"),ResourceBundle.getBundle(I18nUtils.BASE_NAME, Locale.forLanguageTag(lang.replace("_","-"))));
                }
            }
            if(log.isInfoEnabled()){
                Set<String> keys = map.keySet();
                for(String key : keys){
                    log.info(key+":"+map.get(key).getLocale());
                }
            }
        }
    }

    public static ResourceBundle getResourceBundle(String lang){
        ResourceBundle resourceBundle = map.get(lang);
        return resourceBundle == null ? ResourceBundle.getBundle(I18nUtils.BASE_NAME,Locale.getDefault()):resourceBundle;
    }

}
