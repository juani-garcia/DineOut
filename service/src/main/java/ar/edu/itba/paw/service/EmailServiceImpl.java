package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.User;
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

    @Async
    void sendMessageUsingThymeleafTemplate(String to, Map<String, Object> templateModel, String template) {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

        try {
            sendHtmlMessage(to, htmlBody);
        } catch(MessagingException mex) {
            // TODO: Add to logger that mail could not be sent
            System.out.println("*********************************");
            System.out.println("MAIL COULD NOT BE SENT");
            System.out.println("*********************************");
        }
    }

    private void sendHtmlMessage(String to, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom("dineout.graphene@gmail.com");
        helper.setSubject("DineOut");
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }
}