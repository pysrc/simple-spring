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
            if (aClass.isAnnotationPresent(Configuration.class)) {
                Object obj = aClass.newInstance();
                for (Method method : aClass.getDeclaredMethods()) {
                    method.setAccessible(true);
                    if (!method.isAnnotationPresent(Bean.class)) {
                        continue;
                    }
                    Bean bean = method.getAnnotation(Bean.class);
                    BeanMeta meta = new BeanMeta();
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
                    metaMap.put(meta.name, meta);
                }
            } else if (aClass.isAnnotationPresent(Component.class)) {
                Component beanName = aClass.getAnnotation(Component.class);
                BeanMeta meta = new BeanMeta();
                meta.name = aClass.getName();
                meta.beanClass = aClass;
                if (!"".equals(beanName.name())) {
                    meta.name = beanName.name();
                }
                if (metaMap.get(meta.name) != null) {
                    throw new Exception("Bean 定义重复！");
                }
                metaMap.put(meta.name, meta);
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
            return null;
        }
        if (meta.config == null) {
            // 通过Component生成
            bean = meta.beanClass.newInstance();
            beans.put(meta.name, bean);
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
        } else {
            // 通过Configuration产生bean
            Class<?>[] types = meta.method.getParameterTypes();
            Object[] params = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                Class<?> pClass = types[i];
                Object p = this.getBean(pClass);
                if(p == null){
                    throw new Exception(pClass.getName()+" is not bean!");
                }
                params[i] = p;
            }
            bean = meta.method.invoke(meta.config, params);
            beans.put(meta.name, bean);
        }
        return bean;
    }

    @Override
    public <T> T getBean(Class<T> bean) throws Exception {
        return (T) this.getBean(bean.getName());
    }
}
