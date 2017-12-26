package app.application.sdk_base_lib.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * json数据解析工具
 *
 * 注意  待转换bean必须有无参构造函数
 *
 * @author huangkangfa
 */
public class FastJsonUtils {

    /**
     * 使用JSON工具把数据转换成json对象
     * @param value 是对解析的集合的类型
     */
    public static String toString(Object value) {
        String str = JSON.toJSONString(value);
        return str;
    }

    /**
     * 对单个javabean进行解析
     * @param <T>
     * @param json 要解析的json字符串
     * @param cls
     * @return
     */
    public static <T> T getObj(String json, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对list类型进行解析
     * @param <T>
     * @param json 要解析的json字符串
     * @param cls
     * @return
     */
    public static <T> List<T> getListObj(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (!TextUtils.isEmpty(json)) {
            try {
                list = JSON.parseArray(json, cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
