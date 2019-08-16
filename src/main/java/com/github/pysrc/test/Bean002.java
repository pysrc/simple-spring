package com.github.pysrc.test;

import com.github.pysrc.ann.Autowired;
import com.github.pysrc.ann.Component;

@Component
public class Bean002 {
    @Autowired
    Bean001 bean001;

    public Bean001 getBean001() {
        return bean001;
    }

    public void setBean001(Bean001 bean001) {
        this.bean001 = bean001;
    }
}
