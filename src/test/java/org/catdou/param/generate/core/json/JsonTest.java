package org.catdou.param.generate.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.catdou.param.generate.model.UrlMethod;
import org.junit.Test;

/**
 * @author James
 */
public class JsonTest {

    @Test
    public void testJsonFormat() {
        UrlMethod urlMethod = new UrlMethod();
        urlMethod.setUrl("/api/data");
        urlMethod.setMethod("POST");
        System.out.println(JSON.toJSONString(urlMethod, SerializerFeature.PrettyFormat));
    }
}
