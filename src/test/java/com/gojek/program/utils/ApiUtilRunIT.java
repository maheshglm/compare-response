package com.gojek.program.utils;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.gojek.program.SpringTestConfig;
import com.gojek.program.exceptions.Exception;
import com.gojek.program.mdl.ResponseSpec;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class ApiUtilRunIT {

    private static final String HTTP_MOCK_REQUEST = "http://localhost:8080/mockapi";

    @Autowired
    private ApiUtil apiUtil;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Before
    public void setUpMock() {
        stubFor(get(urlPathMatching("/mockapi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        //.withBody("\"testing-library\": \"WireMock\"")));
                        .withBodyFile("mock.json")));
    }

    @Test
    public void testGetApiResponseSpec_validUrl() {
        ResponseSpec apiResponseSpec = apiUtil.getApiResponseSpec(HTTP_MOCK_REQUEST);
        Assert.assertEquals(200, apiResponseSpec.getStatusCode());
        Assert.assertEquals("application/json", apiResponseSpec.getContentType());
        Assert.assertTrue(apiResponseSpec.getResponse().contains("\"first_name\": \"Mock\""));
        Assert.assertTrue(apiResponseSpec.getResponse().contains("\"id\": 3"));
    }

    @Test
    public void testGetApiResponseSpec_invalidUrl() {
        thrown.expect(Exception.class);
        thrown.expectMessage("IO Error while opening HttpUrlConnection");
        apiUtil.getApiResponseSpec("/invalid");
    }


}
