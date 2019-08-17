package com.github.pysrc.test.circle;

import com.github.pysrc.ann.Autowired;
import com.github.pysrc.ann.Component;

@Component
public class CirBean001 {
    @Autowired
    CirBean002 cirBean002;
    String name;

    public CirBean002 getCirBean002() {
        return cirBean002;
    }

    public void setCirBean002(CirBean002 cirBean002) {
        this.cirBean002 = cirBean002;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
