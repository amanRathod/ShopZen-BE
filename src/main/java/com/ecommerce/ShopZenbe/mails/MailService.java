package com.ecommerce.ShopZenbe.mails;

import com.ecommerce.ShopZenbe.models.customer.Customer;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String templateName, Map<String, Object> model)
            throws MessagingException, TemplateException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setText(content, true);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(Customer user) throws MessagingException, TemplateException, IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        sendEmail(user.getEmail(), "Welcome to our website", "welcome.ftl", model);
    }

    public void sendResetPasswordEmail(String resetLink, Customer user) throws MessagingException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("resetLink", resetLink);
        model.put("user", user);
        sendEmail(user.getEmail(), "Reset Password Request", "reset-password.ftl", model);
    }

    public void sendPasswordResetConfirmationEmail(Customer user) throws MessagingException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        sendEmail(user.getEmail(), "Password Reset Confirmation", "reset-password-confirmation.ftl", model);
    }
}
