package com.gojek.program;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringConfig.class})
public class SpringTestConfig {
}
