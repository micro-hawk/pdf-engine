package com.example.demo.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver pdfTemplateResolver = new SpringResourceTemplateResolver();
        pdfTemplateResolver.setPrefix("classpath:/templates/");
        pdfTemplateResolver.setSuffix(".html");
        pdfTemplateResolver.setTemplateMode(TemplateMode.HTML);
        pdfTemplateResolver.setCacheable(false);
        pdfTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return pdfTemplateResolver;
    }

//    @Bean
//    public SpringResourceTemplateResolver cssTemplateResolver() {
//        SpringResourceTemplateResolver cssTemplateResolver = new SpringResourceTemplateResolver();
//        cssTemplateResolver.setPrefix("classpath:/static/css");
//        cssTemplateResolver.setSuffix(".css");
//        cssTemplateResolver.setTemplateMode(TemplateMode.CSS);
//        cssTemplateResolver.setCacheable(false);
//        cssTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        return cssTemplateResolver;
//    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
//        templateEngine.addTemplateResolver(cssTemplateResolver());
        return templateEngine;
    }
}
