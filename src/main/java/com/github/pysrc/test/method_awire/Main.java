package com.github.pysrc.test.method_awire;

import com.github.pysrc.ann.SimpleBootApplication;
import com.github.pysrc.context.ApplicationContext;
import com.github.pysrc.context.Context;

@SimpleBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        Context context = ApplicationContext.run(Main.class, args);
        MBean002 bean002 = context.getBean(MBean002.class);
        MBean001 bean001 = context.getBean(MBean001.class);
        bean001.setName("mbean001");
        System.out.println(bean002.getBean001().getName());
    }
}
