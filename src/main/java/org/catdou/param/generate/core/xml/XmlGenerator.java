package org.catdou.param.generate.core.xml;

import org.catdou.param.generate.config.ApiData;
import org.catdou.param.generate.constant.ParamValidatorGenConstant;
import org.catdou.param.generate.core.BaseGenerator;
import org.catdou.param.generate.model.GenerateParam;
import org.catdou.param.generate.model.UrlMethod;
import org.catdou.param.generate.utils.GenFileUtils;
import org.catdou.validate.constant.ParamValidatorConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author James
 */
public class XmlGenerator implements BaseGenerator {

    @Override
    public void generate(GenerateParam generateParam) {
        GenFileUtils.createDir(generateParam.getParentPath());
        Map<String, List<UrlMethod>> beanUrlMap = ApiData.getBeanUrlMap();
        Set<Map.Entry<String, List<UrlMethod>>> entrySet = beanUrlMap.entrySet();
        for (Map.Entry<String, List<UrlMethod>> entry : entrySet) {
            String beanName = entry.getKey();
            Document document = XmlDocument.createDocument();
            Element urls = document.createElement("urls");
            List<UrlMethod> urlMethodList = entry.getValue();
            for (UrlMethod urlMethod : urlMethodList) {
                Element item = document.createElement("item");
                item.setAttribute("url", urlMethod.getUrl());
                item.setAttribute("method", urlMethod.getMethod());
                urls.appendChild(item);
            }
            document.appendChild(urls);
            String fileName = ParamValidatorConstants.CHECK_RULE_NAME + beanName + ParamValidatorGenConstant.FILE_TYPE_XML;
            String filePath = generateParam.getParentPath() + File.separator + fileName;
            XmlTransformer.transFormDocument(document, filePath);
        }
    }
}
