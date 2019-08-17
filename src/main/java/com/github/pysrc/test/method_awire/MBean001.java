package com.github.pysrc.test.method_awire;

import com.github.pysrc.ann.Component;

@Component
public class MBean001 {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
