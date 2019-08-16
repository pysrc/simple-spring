package com.github.pysrc.context;

public interface Context {
    void putBean(String name, Object bean);
    void putBean(Object bean);
    Object getBean(String name) throws Exception;
    <T> T getBean(Class<T> bean) throws Exception;
}
