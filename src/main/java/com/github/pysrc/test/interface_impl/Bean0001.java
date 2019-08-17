package com.github.pysrc.test.interface_impl;

import com.github.pysrc.ann.Component;

@Component
public class Bean0001 implements IBean0001{
    @Override
    public String getName() {
        return "Hello";
    }
}
