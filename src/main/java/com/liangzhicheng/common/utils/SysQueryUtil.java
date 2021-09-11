package com.liangzhicheng.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.config.page.annotation.Query;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @description 查询工具类
 * @author liangzhicheng
 * @since 2021-09-09
 */
public class SysQueryUtil {

    /**
     * @description 获取查询条件
     * @param obj
     * @param query
     * @param <R>
     * @param <Q>
     * @return QueryWrapper
     */
    public static <R, Q> QueryWrapper getQueryWrapper(R obj, Q query) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<R>();
        if (SysToolUtil.isNull(query)) {
            return queryWrapper;
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query queryAnno = field.getAnnotation(Query.class);
                if (SysToolUtil.isNotNull(queryAnno)) {
                    String propName = queryAnno.propName();
                    String blurry = queryAnno.blurry();
                    String attributeName = SysToolUtil.isBlank(propName) ? field.getName() : propName;
                    attributeName = humpToUnderline(attributeName);
                    Object val = field.get(query);
                    if (SysToolUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    // 模糊多字段
                    if (SysToolUtil.isNotBlank(blurry)) {
                        String[] blurrys = blurry.split(",");
                        queryWrapper.and(wrapper -> {
                            for (int i = 0; i < blurrys.length; i++) {
                                String column = humpToUnderline(blurrys[i]);
                                wrapper.or();
                                wrapper.like(column, val.toString());
                            }
                        });
                        continue;
                    }
                    String finalAttributeName = attributeName;
                    switch (queryAnno.type()) {
                        case EQUAL:
                            queryWrapper.eq(attributeName, val);
                            break;
                        case GREATER_THAN:
                            queryWrapper.ge(finalAttributeName, val);
                            break;
                        case LESS_THAN:
                            queryWrapper.le(finalAttributeName, val);
                            break;
                        case LESS_THAN_NQ:
                            queryWrapper.lt(finalAttributeName, val);
                            break;
                        case INNER_LIKE:
                            queryWrapper.like(finalAttributeName, val);
                            break;
                        case LEFT_LIKE:
                            queryWrapper.likeLeft(finalAttributeName, val);
                            break;
                        case RIGHT_LIKE:
                            queryWrapper.likeRight(finalAttributeName, val);
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>) val)) {
                                queryWrapper.in(finalAttributeName, (Collection<Long>) val);
                            }
                            break;
                        case NOT_EQUAL:
                            queryWrapper.ne(finalAttributeName, val);
                            break;
                        case NOT_NULL:
                            queryWrapper.isNotNull(finalAttributeName);
                            break;
                        case BETWEEN:
                            String[] BETWEEN = ((String) val).split(",");
                            queryWrapper.between(finalAttributeName, BETWEEN[0], BETWEEN[1]);
                            break;
                        case UNIX_TIMESTAMP:
                            String[] UNIX_TIMESTAMP = ((String) val).split(",");
                            if (SysToolUtil.isNotNull(UNIX_TIMESTAMP)) {
                                Date start = SysToolUtil.stringToDate(UNIX_TIMESTAMP[0], null);
                                Date end = SysToolUtil.stringToDate(UNIX_TIMESTAMP[1], null);
                                queryWrapper.between(finalAttributeName, start, end);
                            }
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            e.printStackTrace();
            SysToolUtil.error(e.getMessage(), SysQueryUtil.class);
        }
        return queryWrapper;
    }

    /**
     * @description 获取字段条件
     * @param clazz
     * @param fields
     * @return List<Field>
     */
    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (SysToolUtil.isNotNull(clazz)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * @description 驼峰命名转为下划线命名
     * @param field 驼峰命名字符串
     * @return String
     */
    public static String humpToUnderline(String field) {
        StringBuilder sb = new StringBuilder(field);
        int temp = 0; //定位
        if (!field.contains("_")) {
            for (int i = 0; i < field.length(); i++) {
                if (Character.isUpperCase(field.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString();
    }

}
