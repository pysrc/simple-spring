package com.github.pysrc.test.method_awire;

import com.github.pysrc.ann.Autowired;
import com.github.pysrc.ann.Component;

@Component
public class MBean002 {
    MBean001 bean001;

    @Autowired
    void awire(MBean001 bean001){
        this.bean001 = bean001;
    }

    public MBean001 getBean001() {
        return bean001;
    }

    public void setBean001(MBean001 bean001) {
        this.bean001 = bean001;
    }
}
