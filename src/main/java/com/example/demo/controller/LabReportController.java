package com.example.demo.controller;

import com.example.demo.client.api.HospitalClient;
import com.example.demo.model.dto.MasterHospitalCommonDto;
import com.example.demo.model.response.GlobalResponse;
import com.example.demo.service.api.S3Service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.api.LabReportService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/pdf")
public class LabReportController {

    @Autowired
    private LabReportService labReportService;
    @Autowired
    private HospitalClient hospitalClient;
    @Autowired
    private S3Service s3Service;

    @GetMapping(value = "/lab", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generatePdf() {
        try {
            return labReportService.generateLabReportPdf();
        } catch (Exception ex) {
            log.error("error : {}", ex.getMessage(), ex);
        }
        return null;
    }

    @GetMapping(value = "/html")
    public String generateHTML() throws IOException {
        try {
            return labReportService.generateHTML();
        } catch (Exception ex) {
            log.error("error : {}", ex.getMessage(), ex);
        }
        return null;
    }

    @GetMapping(value = "/hospital/details")
    public Mono<GlobalResponse<MasterHospitalCommonDto>> getCommonDetailsByHospitalCode(
        @RequestParam("hospitalCode") String hospitalCode) {

        return hospitalClient.getCommonDetailsByHospitalCode(hospitalCode);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<byte[]> download(@RequestParam("key") String key) throws IOException {
        log.info("requested key: {}", key);
        return s3Service.getByteDataFromS3(key);
    }
}
