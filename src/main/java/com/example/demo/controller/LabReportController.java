package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.api.LabReportService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/pdf")
public class LabReportController {
    
    @Autowired
    private LabReportService labReportService;

    @GetMapping(value = "/lab", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generatePdf() {
        try {
            return labReportService.generateLabReportPdf();
        } catch (Exception ex) {
            log.error("error : {}", ex.getMessage(), ex);
        }
        return null;
    }
}
