package org.catdou.param.generate.config;

import org.catdou.param.generate.model.UrlMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengdu
 * @date 2019/12/18
 */
public class RequestMethodProvider {

    private List<RequestMappingInfoHandlerMapping> handlerMappings;

    public RequestMethodProvider(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
        initHandlerMapping(handlerMappings);
    }

    private void initHandlerMapping(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        Map<String, List<UrlMethod>> beanUrlMap = new HashMap<>();
        for (RequestMappingInfoHandlerMapping handlerMapping : handlerMappings) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
            Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entrySet = handlerMethodMap.entrySet();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entrySet) {
                RequestMappingInfo requestMapping = entry.getKey();
                String beanName = entry.getValue().getBean().toString();
                List<UrlMethod> urlMethods = beanUrlMap.computeIfAbsent(beanName, k -> new ArrayList<>());
                Object[] patterns = requestMapping.getPatternsCondition().getPatterns().toArray();
                Object[] methods = requestMapping.getMethodsCondition().getMethods().toArray();
                for (Object pattern : patterns) {
                    for (Object method : methods) {
                        UrlMethod url = new UrlMethod();
                        url.setUrl(pattern.toString());
                        url.setMethod(method.toString());
                        urlMethods.add(url);
                    }
                }
                if (CollectionUtils.isEmpty(urlMethods)) {
                    beanUrlMap.remove(beanName);
                }
            }
        }
        ApiData.setBeanUrlMap(beanUrlMap);
    }

    public List<RequestMappingInfoHandlerMapping> getHandlerMappings() {
        return handlerMappings;
    }
}
