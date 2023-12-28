package com.example.demo.service.impl;

import com.example.demo.service.api.LabReportService;
import com.example.demo.service.api.S3Service;
import com.lowagie.text.DocumentException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@Slf4j
public class LabReportServiceImpl implements LabReportService {

    @Autowired
    SpringWebFluxTemplateEngine templateEngine;
    @Autowired
    private S3Service s3Service;

    @Override
    public byte[] generateLabReportPdf() throws IOException, DocumentException {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    @Override
    public String generateHTML() throws IOException {
        Context context = getContext();
        return loadAndFillTemplate(context);
    }

    private static final String PDF_RESOURCES = "/templates/";

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

    private Context getContext() throws IOException {
        byte[] logoBase = s3Service.getByteDataFromS3("aab8f6bb8ac2987a2d0145f8c14dc13c").getBody();
        String hospitalLogo = Base64.getEncoder().encodeToString(logoBase);

        Context context = new Context();
        context.setVariable("HOSPITAL_NAME", "Test Hospital Demo");
        context.setVariable("HOSPITAL_ADDRESS", "Imperial Paradise, Panthur Road, Bellandur, Karnataka, India, 560103");
        context.setVariable("HOSPITAL_PHONE_NUMBER", "9428458865, +917043400140");
        context.setVariable("HOSPITAL_EMAIL_ID", "microhawkx@test.com");

        context.setVariable("MRD_NUMBER", "MRD875JHVS");
        context.setVariable("MOBILE_NUMBER", "7043400140");
        context.setVariable("DOCTOR_NAME", "DR. Doctor Das");
        context.setVariable("PATIENT_FULL_NAME", "MR. Vikas Das");
        context.setVariable("AGE", "22");
        context.setVariable("GENDER", "MALE");
        context.setVariable("PATIENT_ADDRESS", "C-Parth City, Ucharpi Road, Near Kunal Arc, Mehsana");
        context.setVariable("APPOINTMENT_ID", "SKR78253JS");

        context.setVariable("PDF_DATE", "16/12/2023");

        context.setVariable("HOSPITAL_LOGO_BASE64", hospitalLogo);

        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("sample", context);
    }
}
