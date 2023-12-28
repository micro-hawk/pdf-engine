package com.example.demo.service.api;

import java.io.IOException;

import com.lowagie.text.DocumentException;

public interface LabReportService {
    byte[] generateLabReportPdf() throws IOException, DocumentException;
    String generateHTML() throws IOException;
}
