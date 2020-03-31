package com.gp.rabbit.util;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("gpmq");
    }

    public static String getKey(String key) {
        return resourceBundle.getString(key);
    }

}
