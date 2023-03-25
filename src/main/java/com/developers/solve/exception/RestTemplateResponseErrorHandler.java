package com.developers.solve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        // HTTP 응답 코드가 4xx나 5xx인 경우 예외 상황으로 간주합니다.
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        // HTTP 응답 코드가 4xx나 5xx인 경우 처리할 수 있습니다.
        if(statusCode.series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR
        } else if(statusCode.series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
        }
    }
}