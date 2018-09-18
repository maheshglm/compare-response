package com.gojek.program;

import com.gojek.program.utils.FileDirUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public FileDirUtil fileDirUtil(){
        return new FileDirUtil();
    }
}
