package org.catdou.param.generate.model;

import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Set;

/**
 * @author James
 */
public class UrlMethod {
    private String url;

    private String method;

    private List<Parameter> parameterList;

    public UrlMethod() {
    }

    public UrlMethod(String url, String method, List<Parameter> parameterList) {
        this.url = url;
        this.method = method;
        this.parameterList = parameterList;
    }

    private Set<String> pathParamList;

    private Set<String> queryPramList;

    private Set<String> bodyParamList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }

    public Set<String> getPathParamList() {
        return pathParamList;
    }

    public void setPathParamList(Set<String> pathParamList) {
        this.pathParamList = pathParamList;
    }

    public Set<String> getQueryPramList() {
        return queryPramList;
    }

    public void setQueryPramList(Set<String> queryPramList) {
        this.queryPramList = queryPramList;
    }

    public Set<String> getBodyParamList() {
        return bodyParamList;
    }

    public void setBodyParamList(Set<String> bodyParamList) {
        this.bodyParamList = bodyParamList;
    }
}
