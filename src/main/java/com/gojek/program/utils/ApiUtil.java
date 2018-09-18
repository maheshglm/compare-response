package com.gojek.program.utils;

import com.gojek.program.exceptions.Exception;
import com.gojek.program.exceptions.ExceptionType;
import com.gojek.program.mdl.ResponseSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtil.class);

    public static final String IO_ERROR_WHILE_OPENING_HTTP_URL_CONNECTION = "IO Error while opening HttpUrlConnection";

    private static final String GET = "GET";
    private static final String USER_AGENT = "User-Agent";
    private static final String MOZILLA_4_0 = "Mozilla/4.0";

    /**
     * Get Api Response.
     * It opens http connection with the given URL and sends GET request
     * and capture status code, response body and content type into ResponseSpec pojo class
     * @param url
     * @return {@link ResponseSpec}
     *
     */
    public ResponseSpec getApiResponseSpec(final String url) {
        ResponseSpec responseSpec = new ResponseSpec();
        HttpURLConnection httpConn = null;
        StringBuilder response = new StringBuilder();
        String line;
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod(GET);
            httpConn.setRequestProperty(USER_AGENT, MOZILLA_4_0);
            httpConn.setAllowUserInteraction(false);

            responseSpec.setContentType(httpConn.getContentType());
            responseSpec.setStatusCode(httpConn.getResponseCode());

            try (InputStreamReader isReader = new InputStreamReader(httpConn.getInputStream())) {
                try (BufferedReader bufferedReader = new BufferedReader(isReader)) {
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }
            responseSpec.setResponse(response.toString());
            return responseSpec;
        } catch (IOException e) {
            LOGGER.error(IO_ERROR_WHILE_OPENING_HTTP_URL_CONNECTION, e);
            throw new Exception(ExceptionType.IO_ERROR, IO_ERROR_WHILE_OPENING_HTTP_URL_CONNECTION);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }
}
