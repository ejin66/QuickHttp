package com.ejin.quickhttp;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by ejin on 2018/3/27.
 */
class Utils {

    /**
     * 获取泛型T.class
     * T必须是具体类, 若T中还有泛型, 会报错
     */
    static <T> Class<T> getType(Class superClass) {
        try {
            return (Class<T>) ((ParameterizedType) superClass.getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static String convertObj2String(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return (String) o;
        }
        return JSON.toJSONString(o);
    }

    static boolean checkTemplateClass(Class c) {
        int result = 0;
        Field[] fields = c.getDeclaredFields();
        try {
            for (Field item : fields) {
                if (!item.isAccessible()) {
                    item.setAccessible(true);
                }
                Annotation[] annotations = item.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {
                    Class aType = annotation.annotationType();
                    if (aType.equals(Code.class)) {
                        if (item.getType() == int.class) {
                            result |= 1;
                        } else {
                            Log.e("The field with com.ejin.quickhttp.Code annotation must be Type int");
                        }

                    } else if (aType.equals(Error.class) && item.getType() == String.class) {
                        result |= 2;
                    } else if (aType.equals(Data.class)) {
                        result |= 4;
                    }
                }
            }

            if ((result&1) != 1) {
                Log.e("Template class missing com.ejin.quickhttp.Code annotation");
            }

            if ((result&2) != 2) {
                Log.e("Template class missing com.ejin.quickhttp.Error annotation");
            }

            if ((result&4) != 4) {
                Log.e("Template class missing com.ejin.quickhttp.Data annotation");
            }

            return result == 7;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static TempData parseTemplateByAnnotation(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        try {
            TempData data = new TempData();
            for (Field item : fields) {
                if (!item.isAccessible()) {
                    item.setAccessible(true);
                }
                Annotation[] annotations = item.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {
                    Class aType = annotation.annotationType();
                    if (aType.equals(Code.class)) {
                        data.setCode(item.getInt(o));
                    } else if (aType.equals(Error.class)) {
                        data.setError(item.get(o).toString());
                    } else if (aType.equals(Data.class)) {
                        data.setData(item.get(o).toString());
                    }
                }
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
