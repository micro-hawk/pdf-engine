package com.example.demo.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ClientBean {

    @Value("${service.ehr-main.endpoint}")
    String restApiEndpoint;

    @Bean(name = "FlexEhrWebClient")
    public WebClient getFlexEhrWebClient() {
        HttpClient httpClient = HttpClient.create().tcpConfiguration(
            client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).doOnConnected(
                conn -> conn.addHandlerLast(new ReadTimeoutHandler(10))
                    .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
            .baseUrl(restApiEndpoint)
            .exchangeStrategies(ExchangeStrategies
                .builder()
                .codecs(codecs -> codecs
                    .defaultCodecs()
                    .maxInMemorySize(50 * 1024 * 1024))
                .build())
            .clientConnector(connector)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

}
