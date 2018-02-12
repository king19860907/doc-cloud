package com.doc.cloud.base.utils;

import org.apache.shiro.util.StringUtils;
import org.springframework.http.MediaType;

/**
 * Created by majun on 12/02/2018.
 */
public class MediaTypeUtils {

    public static String getMediaType(String suffix){
        if(!StringUtils.hasText(suffix)){
            return MediaType.ALL_VALUE;
        }
        suffix = suffix.toLowerCase();
        if(suffix.equals(MediaEnum.gif.toString())){
            return MediaType.IMAGE_GIF_VALUE;
        }
        if(suffix.equals(MediaEnum.jpg.toString()) || suffix.equals(MediaEnum.jpeg.toString())){
            return MediaType.IMAGE_JPEG_VALUE;
        }
        if(suffix.equals(MediaEnum.png.toString())){
            return MediaType.IMAGE_PNG_VALUE;
        }
        return MediaType.ALL_VALUE;
    }

    enum MediaEnum{
        jpg,
        jpeg,
        gif,
        png,
        js,
        json,
        txt,
        md,
        xml
    }

}
