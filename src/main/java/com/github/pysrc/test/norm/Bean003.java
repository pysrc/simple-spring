package com.github.pysrc.test.norm;


public class Bean003 {
    Bean001 bean001;
    Bean002 bean002;

    public Bean003() {
    }

    public Bean003(Bean001 bean001, Bean002 bean002) {
        this.bean001 = bean001;
        this.bean002 = bean002;
    }

    public Bean001 getBean001() {
        return bean001;
    }

    public void setBean001(Bean001 bean001) {
        this.bean001 = bean001;
    }

    public Bean002 getBean002() {
        return bean002;
    }

    public void setBean002(Bean002 bean002) {
        this.bean002 = bean002;
    }
}
