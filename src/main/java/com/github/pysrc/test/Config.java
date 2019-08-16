package com.github.pysrc.test;

import com.github.pysrc.ann.Bean;
import com.github.pysrc.ann.Configuration;

@Configuration
public class Config {
    @Bean
    Bean003 b3(Bean001 bean001, Bean002 bean002){
        return new Bean003(bean001,bean002);
    }
}
