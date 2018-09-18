package com.gojek.program;

import com.gojek.program.utils.ApiUtil;
import com.gojek.program.svc.CompareSvc;
import com.gojek.program.utils.FileDirUtil;
import com.gojek.program.utils.JsonUtil;
import com.gojek.program.utils.XmlUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public FileDirUtil fileDirUtil(){
        return new FileDirUtil();
    }

    @Bean
    public ApiUtil apiUtil(){
        return new ApiUtil();
    }

    @Bean
    public JsonUtil jsonUtil(){
        return new JsonUtil();
    }

    @Bean
    public XmlUtil xmlUtil(){
        return new XmlUtil();
    }

    @Bean
    public CompareSvc compareSvc(){
        return new CompareSvc();
    }
}
