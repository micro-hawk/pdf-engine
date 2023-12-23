package com.example.demo.client.api;

import com.example.demo.model.dto.MasterHospitalCommonDto;
import com.example.demo.model.response.GlobalResponse;
import reactor.core.publisher.Mono;

public interface HospitalClient {

    Mono<GlobalResponse<MasterHospitalCommonDto>> getCommonDetailsByHospitalCode(String hospitalCode);
}
