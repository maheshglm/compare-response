package com.gojek.program.svc;

import com.gojek.program.SpringTestConfig;
import com.gojek.program.exceptions.Exception;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class CompareSvcRunIT {

    private static final File FILE1 = new File("target/test-classes/gojek/file1.txt");
    private static final File FILE2 = new File("target/test-classes/gojek/file2.txt");

    @Autowired
    private CompareSvc compareSvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCompareApiResponses_successful() {
        try {
            compareSvc.compareApiResponses(FILE1, FILE2);
        }catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testCompareApiResponses_file1NotFound(){
        thrown.expect(Exception.class);
        thrown.expectMessage("noFile.txt] is not available");
        compareSvc.compareApiResponses(new File("noFile.txt"),FILE2);
    }

    @Test
    public void testCompareApiResponses_file2NotFound(){
        thrown.expect(Exception.class);
        thrown.expectMessage("noFile.txt] is not available");
        compareSvc.compareApiResponses(FILE1,new File("noFile.txt"));
    }
}