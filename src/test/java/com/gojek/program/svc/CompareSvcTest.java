package com.gojek.program.svc;

import com.gojek.program.exceptions.Exception;
import com.gojek.program.mdl.ResponseSpec;
import com.gojek.program.utils.JsonUtil;
import com.gojek.program.utils.XmlUtil;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.gojek.program.svc.CompareSvc.APPLICATION_JSON;
import static com.gojek.program.svc.CompareSvc.APPLICATION_XML;
import static com.gojek.program.svc.CompareSvc.UNSUPPORTED_CONTENT_TYPE_FOR_COMPARISION;

public class CompareSvcTest {

    @InjectMocks
    private CompareSvc compareSvc;

    @Mock
    private JsonUtil jsonUtil;

    @Mock
    private XmlUtil xmlUtil;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCompare_Xml() {
        String resp = "<a><b>test</b></a>";

        ResponseSpec spec1 = new ResponseSpec();
        ResponseSpec spec2 = new ResponseSpec();

        spec1.setContentType(APPLICATION_XML);
        spec1.setResponse(resp);
        spec2.setContentType(APPLICATION_XML);
        spec2.setResponse(resp);

        Mockito.when(xmlUtil.compareXml(resp, resp)).thenReturn(true);

        Assert.assertTrue(compareSvc.compare(spec1, spec2));

        Mockito.verify(xmlUtil, Mockito.timeout(1)).compareXml(resp, resp);
    }

    @Test
    public void testCompare_json() {
        String resp = "{\"key\":\"value\"}";

        ResponseSpec spec1 = new ResponseSpec();
        ResponseSpec spec2 = new ResponseSpec();

        spec1.setContentType(APPLICATION_JSON);
        spec1.setResponse(resp);
        spec2.setContentType(APPLICATION_JSON);
        spec2.setResponse(resp);

        Mockito.when(jsonUtil.compareJson(resp, resp)).thenReturn(true);

        Assert.assertTrue(compareSvc.compare(spec1, spec2));

        Mockito.verify(jsonUtil, Mockito.timeout(1)).compareJson(resp, resp);
    }

    @Test
    public void testCompare_invalidContentType() {
        thrown.expect(Exception.class);
        thrown.expectMessage(UNSUPPORTED_CONTENT_TYPE_FOR_COMPARISION);

        String resp = "{\"key\":\"value\"}";

        ResponseSpec spec1 = new ResponseSpec();
        ResponseSpec spec2 = new ResponseSpec();

        spec1.setContentType("application/text");
        spec1.setResponse(resp);
        spec2.setContentType("application/text");
        spec2.setResponse(resp);

        Assert.assertTrue(compareSvc.compare(spec1, spec2));

        Mockito.verify(jsonUtil, Mockito.timeout(0)).compareJson(resp, resp);
    }


}

