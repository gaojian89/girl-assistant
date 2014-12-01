package com.girlassistant.imageloader;

import com.girlassistant.utils.StringUtils;

/**
 * Url的处理
 *
 * @author gaoj
 */
public class CacheHelper {

    public static String getFileNameFromUrl(String url) {
        return StringUtils.getMD5Str(url);
    }
}