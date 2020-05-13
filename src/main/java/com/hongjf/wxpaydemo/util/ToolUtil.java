package com.hongjf.wxpaydemo.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright 2019 ZhiLiao, Inc. All rights reserved.
 *
 * @Author: Hongjf
 * @Date: 2019/9/27
 * @Time: 16:02
 * @Description:判断工具类
 */
public class ToolUtil {
    /**
     * 对象是否为空
     *
     * @author fengshuonan
     * @Date 2018/3/18 21:57
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if (o.toString().trim().equals("")) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof Long) {
            if (o == null) {
                return true;
            }
        } else if (o instanceof Integer) {
            if (o == null) {
                return true;
            }
        } else if (o instanceof Double) {
            if (o == null) {
                return true;
            }
        } else if (o instanceof Boolean) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在空对象
     *
     * @author fengshuonan
     * @Date 2018/3/18 21:59
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            if (o.toString().trim().equals("")) {
                return false;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return false;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return false;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return false;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return false;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return false;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return false;
            }
        } else if (o instanceof double[]) {
            if (((double[]) o).length == 0) {
                return false;
            }
        } else if (o instanceof Long) {
            if (o == null) {
                return false;
            }
        } else if (o instanceof Integer) {
            if (o == null) {
                return false;
            }
        } else if (o instanceof Double) {
            if (o == null) {
                return false;
            }
        } else if (o instanceof Boolean) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOneNotEmpty(Object... os) {
        for (Object o : os) {
            if (isNotEmpty(o)) {
                return true;
            }
        }
        return false;
    }
}
