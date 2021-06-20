package org.catdou.param.generate.core.json;

import org.catdou.param.generate.config.ApiData;
import org.catdou.param.generate.core.BaseGenerator;
import org.catdou.param.generate.model.GenerateParam;
import org.catdou.param.generate.model.UrlMethod;
import org.catdou.param.generate.utils.GenFileUtils;
import org.catdou.validate.log.ValidatorLog;
import org.catdou.validate.log.ValidatorLogFactory;
import org.catdou.validate.model.config.Param;
import org.catdou.validate.model.config.UrlRuleBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author James
 */
public class JsonGeneratorWithSwagger extends JsonGenerator implements BaseGenerator {
    private ValidatorLog LOG = ValidatorLogFactory.getLogger(JsonGeneratorWithSwagger.class);

    @Override
    public void generate(GenerateParam generateParam) {
        GenFileUtils.createDir(generateParam.getParentPath());
        Map<String, List<UrlMethod>> beanUrlMap = ApiData.getBeanUrlMap();
        Set<Map.Entry<String, List<UrlMethod>>> entrySet = beanUrlMap.entrySet();
        for (Map.Entry<String, List<UrlMethod>> entry : entrySet) {
            String beanName = entry.getKey();
            List<UrlRuleBean> urlRuleBeanList = new ArrayList<>();
            List<UrlMethod> urlMethodList = entry.getValue();
            for (UrlMethod urlMethod : urlMethodList) {
                UrlRuleBean urlRuleBean = new UrlRuleBean();
                urlRuleBean.setUrl(urlMethod.getUrl());
                urlRuleBean.setMethod(urlMethod.getMethod());
                List<Param> urlParamList = new ArrayList<>();
                List<Param> pathParamList = new ArrayList<>();
                List<Param> bodyParamList = new ArrayList<>();
                convertStrToParam(urlMethod.getQueryPramList(), urlParamList);
                convertStrToParam(urlMethod.getPathParamList(), pathParamList);
                convertStrToParam(urlMethod.getBodyParamList(), bodyParamList);
                urlRuleBean.setUrlParams(urlParamList);
                urlRuleBean.setPathParams(pathParamList);
                urlRuleBean.setBodyParams(bodyParamList);
                urlRuleBeanList.add(urlRuleBean);
            }
            writeJsonToFile(generateParam, beanName, urlRuleBeanList);
        }
    }

    private void convertStrToParam(Set<String> strParamList, List<Param> paramList) {
        for (String queryParam : strParamList) {
            Param param = new Param();
            param.setName(queryParam);
            param.setRules(new ArrayList<>());
            paramList.add(param);
        }
    }


}
