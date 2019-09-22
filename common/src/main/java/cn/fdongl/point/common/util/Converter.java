package cn.fdongl.point.common.util;

import org.springframework.beans.BeanUtils;

public class Converter {

    public static <S,T> T convert(S t,Class<T>clazz){
        T target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(t, target);
        return target;
    }

}
