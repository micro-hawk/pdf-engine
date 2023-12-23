package com.example.demo.client.impl;

import com.example.demo.client.api.HospitalClient;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.ApiResponseStatus;
import com.example.demo.model.dto.MasterHospitalCommonDto;
import com.example.demo.model.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HospitalClientImpl implements HospitalClient {

    @Autowired
    @Qualifier(value = "FlexEhrWebClient")
    WebClient flexEhrWebClient;

    public Mono<GlobalResponse<MasterHospitalCommonDto>> getCommonDetailsByHospitalCode(String hospitalCode) {
        return flexEhrWebClient.get()
            .uri(uriBuilder -> uriBuilder.path("/masterHospital/details")
                .queryParam("hospitalCode", hospitalCode)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(clientResponse -> {
                if (clientResponse.statusCode().isError()) {
                    return clientResponse.bodyToMono(GlobalResponse.class).flatMap(err -> {
                        log.error("Error in #getCommonDetailsByHospitalCode response with code: {} and message: {}",
                            err.getResponseCode(), err.getMessage());
                        return Mono.error(new BusinessException(
                            ApiResponseStatus.getInstance(String.valueOf(err.getResponseCode()))));
                    });
                }
                return clientResponse.bodyToMono(
                    new ParameterizedTypeReference<GlobalResponse<MasterHospitalCommonDto>>() {
                    });
            });
    }
}
