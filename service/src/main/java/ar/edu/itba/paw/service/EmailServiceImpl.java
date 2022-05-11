package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendAccountCreationMail(String to, String name) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        sendMessageUsingThymeleafTemplate(to, model, "account-creation.html");
    }

    @Override
    public void sendReservationCreatedUser(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("restaurant", reservation.getRestaurant().getName());
        model.put("amount", reservation.getAmount());
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-created-user.html");
    }

    @Override
    public void sendReservationCreatedRestaurant(String to, String name, Reservation reservation, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", reservation.getRestaurant().getName());
        model.put("date", reservation.getDateString());
        model.put("amount", reservation.getAmount());
        model.put("time", reservation.getTimeString());
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getFirstName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-created-restaurant.html");
    }

    @Override
    public void sendReservationCancelledUser(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("restaurant", reservation.getRestaurant().getName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-cancelled-user.html");
    }

    @Override
    public void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", reservation.getRestaurant().getName());
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getFirstName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-cancelled-restaurant.html");
    }

    @Override
    public void sendReservationConfirmed(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("restaurant", reservation.getRestaurant().getName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-confirmed.html");
    }

    @Override
    public void sendChangePassword(String to, String name, String recoveryLink) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("recovery_link", recoveryLink);
        sendMessageUsingThymeleafTemplate(to, model, "reset-password.html");
    }

    public void sendMessageUsingThymeleafTemplate(String to, Map<String, Object> templateModel, String template) {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

        try {
            sendHtmlMessage(to, htmlBody);
        } catch (MessagingException mex) {
            LOGGER.warn("Could not send email to {} with template {}", to, template, mex);
        }
    }

    @Async
    public void sendHtmlMessage(String to, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom("dineout.graphene@gmail.com");
        helper.setSubject("DineOut");
        String plainTextBody = htmlBody.replaceAll("((<script>[^<]*</script>)|(<style>[^<]*</style>))|(<[^>]*>)", "");  // As per: https://www.baeldung.com/java-remove-html-tags this could bring some issues but for our uses it is enough.
        helper.setText(plainTextBody, htmlBody);
        emailSender.send(message);
    }
}