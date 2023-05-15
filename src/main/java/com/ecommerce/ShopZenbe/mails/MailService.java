package com.ecommerce.ShopZenbe.mails;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendWelcomeEmail(String toEmail, String name) throws MessagingException, TemplateException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("aksrathod07@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("Welcome to our website");

        Map<String, Object> model = new HashMap<>();
        model.put("name", name);

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("welcome.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
