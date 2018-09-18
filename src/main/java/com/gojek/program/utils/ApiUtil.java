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

    public static ResponseSpec getApiResponseSpec(final String url) {
        ResponseSpec responseSpec = new ResponseSpec();
        HttpURLConnection httpConn;
        StringBuilder response = new StringBuilder();
        String line;
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0");
            httpConn.setAllowUserInteraction(false);
            try (InputStreamReader isReader = new InputStreamReader(httpConn.getInputStream())) {
                try (BufferedReader bufferedReader = new BufferedReader(isReader)) {
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }
            responseSpec.setContentType(httpConn.getContentType());
            responseSpec.setStatusCode(httpConn.getResponseCode());
            responseSpec.setResponse(response.toString());
            return responseSpec;
        } catch (IOException e) {
            LOGGER.error("IO Error while opening HttpUrlConnection");
            throw new Exception(ExceptionType.IO_ERROR, "IO Error while opening HttpUrlConnection");
        }
    }
}
