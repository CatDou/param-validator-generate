package org.catdou.param.generate.factory;

import org.catdou.param.generate.constant.GenTypeEnum;
import org.catdou.param.generate.core.BaseGenerator;
import org.catdou.param.generate.core.json.JsonGenerator;
import org.catdou.param.generate.core.xml.XmlGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James
 */
public class GenerateFactory {
    private static Map<GenTypeEnum, BaseGenerator> GEN_MAP = new HashMap<>();

    static {
        GEN_MAP.put(GenTypeEnum.JSON, new JsonGenerator());
        GEN_MAP.put(GenTypeEnum.XML, new XmlGenerator());
    }

    public static BaseGenerator createGenerator(GenTypeEnum genTypeEnum) {
        BaseGenerator baseGenerator = GEN_MAP.get(genTypeEnum);
        if (baseGenerator == null) {
            throw new IllegalArgumentException("unknown gentype enum " + genTypeEnum.name());
        }
        return baseGenerator;
    }
}
