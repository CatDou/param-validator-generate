package org.catdou.param.generate.utils;

import java.io.File;

/**
 * @author James
 */
public class GenFileUtils {

    public static void createDir(String parentPath) {
        File file = new File(parentPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
