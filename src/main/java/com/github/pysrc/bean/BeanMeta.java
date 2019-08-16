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
}
