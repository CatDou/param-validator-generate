package org.catdou.param.generate.model;

import org.catdou.param.generate.constant.GenTypeEnum;

/**
 * @author James
 */
public class GenerateParam {
    private GenTypeEnum type;

    private String parentPath;

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public GenTypeEnum getType() {
        return type;
    }

    public void setType(GenTypeEnum type) {
        this.type = type;
    }
}
