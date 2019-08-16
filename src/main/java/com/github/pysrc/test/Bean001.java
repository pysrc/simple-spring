package com.github.pysrc.test;

import com.github.pysrc.ann.Component;

@Component
public class Bean001 {
    String name = "Bean001";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
