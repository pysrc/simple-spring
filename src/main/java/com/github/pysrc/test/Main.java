package com.github.pysrc.test;

import com.github.pysrc.ann.SimpleBootApplication;
import com.github.pysrc.context.Context;
import com.github.pysrc.context.ApplicationContext;

@SimpleBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        Context context = ApplicationContext.run(Main.class, args);
        Bean003 bean003 = context.getBean(Bean003.class);
        System.out.println(bean003.getBean002().getBean001().getName());
    }
}
