package com.gojek.program;

import com.gojek.program.svc.CompareSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            LOGGER.error("Programs requires 2 arguments, Exiting...");
            System.exit(3);
        }
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        CompareSvc bean = context.getBean(CompareSvc.class);
        bean.compareApiResponses(file1, file2);
    }
}
