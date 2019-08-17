package com.github.pysrc.test.interface_impl;

import com.github.pysrc.ann.SimpleBootApplication;
import com.github.pysrc.context.ApplicationContext;
import com.github.pysrc.context.Context;

@SimpleBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        Context context = ApplicationContext.run(Main.class, args);
        IBean0001 bean0001 = context.getBean(IBean0001.class);
        System.out.println(bean0001.getName());
    }
}
