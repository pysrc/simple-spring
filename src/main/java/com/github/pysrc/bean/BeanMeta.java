package com.github.pysrc.bean;

import java.lang.reflect.Method;

/**
 * Bean规范信息
 */
public class BeanMeta {
    public String name; // bean name
    public Class beanClass; // bean class
    public Object config; // 配置类
    public Method method; // 配置类调用的生成方法

    public BeanMeta copy() {
        BeanMeta meta = new BeanMeta();
        meta.name = name;
        meta.beanClass = beanClass;
        meta.config = config;
        meta.method = method;
        return meta;
    }
}
