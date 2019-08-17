package com.github.pysrc.test.norm;

import com.github.pysrc.ann.SimpleBootApplication;
import com.github.pysrc.context.Context;
import com.github.pysrc.context.ApplicationContext;

@SimpleBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        Context context = ApplicationContext.run(Main.class, args);
        // 从容器中取出bean003
        Bean003 bean003 = context.getBean(Bean003.class);


        // 此时bean001的name还为空
        System.out.println("------------------Bean001'name is null------------------------");
        System.out.println(bean003.getBean002().getBean001().getName());

        // 将容器中bean001的name字段设为非空
        Bean001 bean001 = context.getBean(Bean001.class);
        bean001.setName("This is bean001 !");
        System.out.println("------------------Bean001'name is not null------------------------");
        System.out.println(bean003.getBean002().getBean001().getName());
    }
}
