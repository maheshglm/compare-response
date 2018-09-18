package com.gojek.program;

import com.gojek.program.svc.SvcSpringConfig;
import com.gojek.program.utils.UtilSpringConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UtilSpringConfig.class, SvcSpringConfig.class})
public class SpringTestConfig {
}
