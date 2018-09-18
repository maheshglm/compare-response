package com.gojek.program.svc;

import com.gojek.program.utils.ApiUtil;
import com.gojek.program.utils.FileDirUtil;
import com.gojek.program.utils.JsonUtil;
import com.gojek.program.utils.XmlUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SvcSpringConfig {

    @Bean
    public CompareSvc compareSvc(){
        return new CompareSvc();
    }
}
