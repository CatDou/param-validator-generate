package org.catdou.param.generate.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.catdou.param.generate.config.ApiData;
import org.catdou.param.generate.constant.ParamValidatorGenConstant;
import org.catdou.param.generate.core.BaseGenerator;
import org.catdou.param.generate.model.GenerateParam;
import org.catdou.param.generate.model.UrlMethod;
import org.catdou.validate.constant.ParamValidatorConstants;
import org.catdou.validate.log.ValidatorLog;
import org.catdou.validate.log.ValidatorLogFactory;
import org.catdou.validate.model.config.Param;
import org.catdou.validate.model.config.UrlRuleBean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author James
 */
public class JsonGenerator implements BaseGenerator {
    private ValidatorLog LOG = ValidatorLogFactory.getLogger(JsonGenerator.class);

    @Override
    public void generate(GenerateParam generateParam) {
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
                List<Param> paramList = new ArrayList<>();
                paramList.add(new Param());
                List<Param> pathParamList = new ArrayList<>();
                pathParamList.add(new Param());
                List<Param> bodyParamList = new ArrayList<>();
                bodyParamList.add(new Param());
                urlRuleBean.setUrlParams(paramList);
                urlRuleBean.setPathParams(pathParamList);
                urlRuleBean.setBodyParams(bodyParamList);
                urlRuleBeanList.add(urlRuleBean);
            }
            String json = JSON.toJSONString(urlRuleBeanList, SerializerFeature.PrettyFormat);
            String fileName = ParamValidatorConstants.CHECK_RULE_NAME + beanName + ParamValidatorGenConstant.FILE_TYPE_JSON;
            String filePath = generateParam.getParentPath() + File.separator + fileName;
            try {
                FileUtils.write(new File(filePath), json, StandardCharsets.UTF_8);
            } catch (IOException e) {
                LOG.error("write json to file error " + ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
