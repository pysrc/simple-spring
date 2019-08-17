package com.github.pysrc.test.circle;

import com.github.pysrc.ann.Autowired;
import com.github.pysrc.ann.Component;

@Component
public class CirBean002 {
    @Autowired
    CirBean001 cirBean001;

    public CirBean001 getCirBean001() {
        return cirBean001;
    }

    public void setCirBean001(CirBean001 cirBean001) {
        this.cirBean001 = cirBean001;
    }
}
