package com.developers.solve.config;

import com.developers.solve.exception.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * restTemplate()
 * RestTemplate 빈을 등록해두고 커스텀 예외 클래스를 주입받아서 예외 처리 진행
 */

@Configuration
@Slf4j
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    } public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
            return (request, body, execution) -> {
                RetryTemplate retryTemplate = new RetryTemplate();
                retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
                try {
                    return retryTemplate.execute(context -> execution.execute(request, body));
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            };
        }
    static class LoggingInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution ex) throws IOException {
            final String sessionNumber = makeSessionNumber();
            printRequest(sessionNumber, req, body);
            ClientHttpResponse response = ex.execute(req, body);
            printResponse(sessionNumber, response);
            return response;
        }

        private String makeSessionNumber() {
            return Integer.toString((int) (Math.random() * 1000000));
        }

        private void printRequest(final String sessionNumber, final HttpRequest req, final byte[] body) {
            log.info("[{}] URI: {}, Method: {}, Headers:{}, Body:{} ",
                    sessionNumber, req.getURI(), req.getMethod(), req.getHeaders(), new String(body, StandardCharsets.UTF_8));
        }

        private void printResponse(final String sessionNumber, final ClientHttpResponse res) throws IOException {
            String body = new BufferedReader(new InputStreamReader(res.getBody(), StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            log.info("[{}] Status: {}, Headers:{}, Body:{} ",
                    sessionNumber, res.getStatusCode(), res.getHeaders(), body);
        }
    }
}
