package com.github.pysrc.test.circle;

import com.github.pysrc.ann.SimpleBootApplication;
import com.github.pysrc.context.ApplicationContext;
import com.github.pysrc.context.Context;

@SimpleBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        Context context = ApplicationContext.run(Main.class, args);
        CirBean002 bean002 = context.getBean(CirBean002.class);
        CirBean001 bean001 = context.getBean(CirBean001.class);
        bean001.setName("Set bean001");
        System.out.println(bean002.getCirBean001().getName());
    }
}
