package org.catdou.param.generate.config;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.Property;
import org.catdou.param.generate.model.UrlMethod;
import org.catdou.validate.constant.ParamValidatorConstants;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author James
 */
public class SwaggerDataProvider {
    private Swagger swagger;

    public SwaggerDataProvider(Swagger swagger) {
        this.swagger = swagger;
    }

    public void initSwaggerData() {
        Map<String, Path> pathMap = swagger.getPaths();
        Map<String, List<UrlMethod>> tagsPathMap = new HashMap<>();
        pathMap.forEach((key, path) -> {
            if (path.getGet() != null) {
                putTagPathMap(tagsPathMap, key,"GET",  path.getGet());
            }
            if (path.getPost() != null) {
                putTagPathMap(tagsPathMap, key,"POST", path.getPost());
            }
            if (path.getPut() != null) {
                putTagPathMap(tagsPathMap, key,"PUT", path.getPut());
            }
            if (path.getDelete() != null) {
                putTagPathMap(tagsPathMap, key,"DELETE", path.getDelete());
            }
        });
        tagsPathMap.forEach((tag, pathList) -> {
            convertToStrParam(pathList);
        });
        ApiData.setBeanUrlMap(tagsPathMap);
    }

    private void convertToStrParam(List<UrlMethod> pathList) {
        pathList.forEach(urlPath -> {
            List<Parameter> parameterList = urlPath.getParameterList();
            List<PathParameter> pathParameterList = new ArrayList<>();
            List<QueryParameter> queryParameterList = new ArrayList<>();
            List<BodyParameter> bodyParameterList = new ArrayList<>();
            parameterList.forEach(parameter -> {
                if (parameter instanceof PathParameter) {
                    pathParameterList.add((PathParameter) parameter);
                }
                if (parameter instanceof QueryParameter) {
                    queryParameterList.add((QueryParameter) parameter);
                }
                if (parameter instanceof BodyParameter) {
                    bodyParameterList.add((BodyParameter) parameter);
                }
            });
            setParamList(urlPath, pathParameterList, queryParameterList, bodyParameterList);
        });
    }

    private void setParamList(UrlMethod urlPath, List<PathParameter> pathParameterList, List<QueryParameter> queryParameterList, List<BodyParameter> bodyParameterList) {
        Set<String> pathParamList = new HashSet<>();
        Set<String> queryPramList = new HashSet<>();
        Set<String> bodyParamList = new HashSet<>();
        urlPath.setPathParamList(pathParamList);
        urlPath.setQueryPramList(queryPramList);
        urlPath.setBodyParamList(bodyParamList);
        pathParameterList.forEach(parameter -> {
            String paramName = parameter.getName();
            paramName = ParamValidatorConstants.PATH_LEFT + paramName + ParamValidatorConstants.PATH_RIGHT;
            pathParamList.add(paramName);
        });
        queryParameterList.forEach(queryParameter -> {
            String paramName = queryParameter.getName();
            int leftIndex = paramName.indexOf("[");
            if (leftIndex != -1) {
                paramName = paramName.substring(0, leftIndex);
            }
            queryPramList.add(paramName);
        });
        Map<String, Model> modelMap = swagger.getDefinitions();
        bodyParameterList.forEach(bodyParameter -> {
            Model model = bodyParameter.getSchema();
            if (model instanceof RefModel) {
                RefModel refModel = (RefModel) model;
                Model dataModel = modelMap.get(refModel.getSimpleRef());
                Map<String, Property> propertyMap = dataModel.getProperties();
                bodyParamList.addAll(propertyMap.keySet());
            }
        });
    }

    private void putTagPathMap(Map<String, List<UrlMethod>> tagsPathMap, String key, String method, Operation operation) {
        List<String> tags = operation.getTags();
        List<Parameter> parameterList = operation.getParameters();
        if (!CollectionUtils.isEmpty(tags) && !CollectionUtils.isEmpty(parameterList)) {
            UrlMethod urlMethod = new UrlMethod(key, method, parameterList);
            List<UrlMethod> urlMethodList = tagsPathMap.computeIfAbsent(tags.get(0), k -> new ArrayList<>());
            urlMethodList.add(urlMethod);
        }
    }
}
