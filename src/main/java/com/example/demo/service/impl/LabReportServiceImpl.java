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
public class LabReportServiceImpl implements LabReportService {
 
    @Autowired
    SpringWebFluxTemplateEngine templateEngine;

    @Override
    public byte[] generateLabReportPdf() throws IOException, DocumentException {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
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

    private Context getContext() {
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
        context.setVariable("INITIAL_AMOUNT","10000");
        context.setVariable("INSURANCE_DISCOUNT","10%");
        context.setVariable("TOTAL_DISCOUNT","1000");
        context.setVariable("BILL_AMOUNT","2000");
        context.setVariable("PAYMENT_MODE","ONLINE");
        context.setVariable("TRANSACTION_ID","09932758793");
        context.setVariable("RECEPTION_NAME","Ritik Pal");
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("hospital", context);
    }
}
