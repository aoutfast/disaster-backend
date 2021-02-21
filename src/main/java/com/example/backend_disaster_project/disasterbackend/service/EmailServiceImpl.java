package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.properties.EmailProperties;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private Configuration configuration;

    @Autowired
    private  JavaMailSender javaMailSender;
    @Autowired
    private  EmailProperties emailProperties;

    @Value("${client.url}")
    String clientUrl;

    @Override
    @Async("threadPoolEmailTask")
    public void sendEmailVerificationEmail(VictimDB userDto, String appBaseUrl) {
        // prepare email meta data
        String recipientAddress = userDto.getEmail();
        String subject = emailProperties.getSubjectRegisterVerification();
        String confirmationUrl = appBaseUrl + emailProperties.getUrlRegisterVerification() + userDto.getEmailVerificationToken();
        String body = getEmailBody(confirmationUrl, userDto.getName(), "email-verification.ftl");
        // send email
        sendEmail(recipientAddress, subject, body);
    }

    @Override
    @Async("threadPoolEmailTask")
    public void sendPasswordResetEmail(VictimDB userDto, String token) {
        // prepare email meta data
        String recipientAddress = userDto.getEmail();
        String subject = emailProperties.getSubjectPasswordReset();
        // confirmationUrl - front end url, shows form for password reset.
        String confirmationUrl = emailProperties.getUrlPasswordReset() + token;
        String body = getEmailBody(confirmationUrl, userDto.getName(), "password-reset-request.ftl");
        // send email
        sendEmail(recipientAddress, subject, body);
    }


    private void sendEmail(String recipientAddress, String subject, String body) {
        try {
            MimeMessage email = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email);
            helper.setFrom(emailProperties.getSenderAddress());
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(email);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getEmailBody(String url, String name, String templateName) {
        try {
            Template template = configuration.getTemplate(templateName);
            Map<String, String> map = new HashMap<>();
            map.put("USER_ACTION_LINK", url);
            map.put("NAME", name);
            map.put("FRONT_END_URL", clientUrl);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
