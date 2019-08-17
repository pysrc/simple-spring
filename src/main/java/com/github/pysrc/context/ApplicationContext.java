package com.github.pysrc.context;

import com.github.pysrc.ann.*;
import com.github.pysrc.bean.BeanMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动类
 */
public class ApplicationContext implements Context {

    static Map<String, BeanMeta> metaMap = new HashMap<>();
    static Map<String, Object> beans = new HashMap<>();

    public static Context run(Class mainClass, String[] args) throws Exception {
        if (!mainClass.isAnnotationPresent(SimpleBootApplication.class)) {
            return null;
        }
        String name = mainClass.getName();
        String basePath = name.replaceAll("\\.[^\\.]+$", "");
        List<String> className = PackageUtil.getClassName(basePath);
        // 扫描bean定义元信息
        for (String c : className) {
            Class<?> aClass = Class.forName(c);
            BeanMeta meta = new BeanMeta();
            if (aClass.isAnnotationPresent(Configuration.class)) {
                Object obj = aClass.newInstance();
                for (Method method : aClass.getDeclaredMethods()) {
                    method.setAccessible(true);
                    if (!method.isAnnotationPresent(Bean.class)) {
                        continue;
                    }
                    Bean bean = method.getAnnotation(Bean.class);
                    meta.beanClass = method.getReturnType();
                    meta.name = meta.beanClass.getName();
                    meta.config = obj;
                    meta.method = method;
                    if (!"".equals(bean.name())) {
                        meta.name = bean.name();
                    }
                    if (metaMap.get(meta.name) != null) {
                        throw new Exception("Bean 定义重复！");
                    }
                }
            } else if (aClass.isAnnotationPresent(Component.class)) {
                Component beanName = aClass.getAnnotation(Component.class);
                meta.name = aClass.getName();
                meta.beanClass = aClass;
                if (!"".equals(beanName.name())) {
                    meta.name = beanName.name();
                }
                if (metaMap.get(meta.name) != null) {
                    throw new Exception("Bean 定义重复！");
                }
            } else {
                continue;
            }
            metaMap.put(meta.name, meta);
            // 将实例注册到接口
            Class[] interfaces = meta.beanClass.getInterfaces();
            if (interfaces != null) {
                for (Class inter : interfaces) {
                    BeanMeta copy = meta.copy();
                    copy.name = inter.getName();
                    if (metaMap.get(copy.name) != null) {
                        throw new Exception(String.format("Bean name %s is repeat", copy.name));
                    }
                    metaMap.put(copy.name, copy);
                }
            }

        }
        return new ApplicationContext();
    }

    @Override
    public void putBean(String name, Object bean) {
        beans.put(name, bean);
    }

    @Override
    public void putBean(Object bean) {
        beans.put(bean.getClass().getName(), bean);
    }

    @Override
    public Object getBean(String name) throws Exception {
        Object bean = beans.get(name);
        // Bean已经存在
        if (bean != null) {
            return bean;
        }
        // Bean 不存在
        // 这一步执行生成方法，生成bean
        BeanMeta meta = metaMap.get(name);
        if (meta == null) {
            throw new Exception(String.format("Bean %s is not define", name));
        }
        if (meta.config == null) {
            // 通过Component生成
            bean = meta.beanClass.newInstance();
            beans.put(meta.name, bean);
        } else {
            // 通过Configuration产生bean
            Class<?>[] types = meta.method.getParameterTypes();
            Object[] params = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                Class<?> pClass = types[i];
                Object p = this.getBean(pClass);
                if (p == null) {
                    throw new Exception(pClass.getName() + " is not bean!");
                }
                params[i] = p;
            }
            bean = meta.method.invoke(meta.config, params);
            beans.put(meta.name, bean);
        }

        // 处理依赖---------------------------------------------------------------
        // 属性注入
        for (Field field : meta.beanClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired annotation = field.getAnnotation(Autowired.class);
                String bname = field.getType().getName();
                if (!"".equals(annotation.name())) {
                    bname = annotation.name();
                }
                field.set(bean, this.getBean(bname));
            }
        }
        // 方法注入
        for (Method method : meta.beanClass.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(Autowired.class)) {
                Class<?>[] types = method.getParameterTypes();
                Object[] params = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    params[i] = this.getBean(types[i]);
                }
                method.invoke(bean, params);
            }
        }
        return bean;
    }

    @Override
    public <T> T getBean(Class<T> bean) throws Exception {
        return (T) this.getBean(bean.getName());
    }
}
