package org.catdou.param.generate.config;

import org.catdou.param.generate.model.UrlMethod;

import java.util.List;
import java.util.Map;

/**
 * @author James
 */
public class ApiData {
    private static Map<String, List<UrlMethod>> beanUrlMap;

    public static Map<String, List<UrlMethod>> getBeanUrlMap() {
        return beanUrlMap;
    }

    public static void setBeanUrlMap(Map<String, List<UrlMethod>> beanUrlMap) {
        ApiData.beanUrlMap = beanUrlMap;
    }
}
