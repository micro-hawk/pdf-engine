package com.example.demo.service.impl;


import com.example.demo.service.api.LabReportService;
import com.lowagie.text.DocumentException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@Slf4j
public class LabReportServiceImpl implements LabReportService{


    @Autowired
    SpringWebFluxTemplateEngine templateEngine;

    @Override
    public byte[] generateLabReportPdf() throws IOException, DocumentException {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }


    private static final String PDF_RESOURCES = "/pdf-templates/";

    private byte[] renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("example-test", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();

        return Files.readAllBytes(file.toPath());
    }


    private Context getContext() {
        Context context = new Context();
        context.setVariable("to", "FlexEHR");
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("LabReport", context);
    }
}
