package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String ADDRESS = "dineout.graphene@gmail.com";
    private static final String SENDER_NAME = "DineOut";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private MessageSource messageSource;

    @Async
    @Override
    public void sendAccountCreationMail(String to, String name) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        sendMessageUsingThymeleafTemplate(to, model, "account-creation.html", "subject.account_creation");
    }

    @Async
    @Override
    public void sendReservationCreatedUser(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("restaurant", reservation.getRestaurant().getName());
        model.put("amount", reservation.getAmount());
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-created-user.html", "subject.reservation_created");
    }

    @Async
    @Override
    public void sendReservationCreatedRestaurant(String to, String name, Reservation reservation, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", reservation.getRestaurant().getName());
        model.put("date", reservation.getDateString());
        model.put("amount", reservation.getAmount());
        model.put("time", reservation.getTimeString());
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getFirstName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-created-restaurant.html", "subject.reservation_created");
    }

    @Async
    @Override
    public void sendReservationCancelledUser(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("restaurant", reservation.getRestaurant().getName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-cancelled-user.html", "subject.reservation_cancelled");
    }

    @Async
    @Override
    public void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", reservation.getRestaurant().getName());
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getFirstName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-cancelled-restaurant.html", "subject.reservation_cancelled");
    }

    @Async
    @Override
    public void sendReservationConfirmed(String to, String name, Reservation reservation) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("date", reservation.getDateString());
        model.put("time", reservation.getTimeString());
        model.put("restaurant", reservation.getRestaurant().getName());
        sendMessageUsingThymeleafTemplate(to, model, "reservation-confirmed.html", "subject.reservation_confirmed");
    }

    @Async
    @Override
    public void sendChangePassword(String to, String name, String recoveryLink) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipient", name);
        model.put("recovery_link", recoveryLink);
        sendMessageUsingThymeleafTemplate(to, model, "reset-password.html", "subject.reset_password");
    }

    private void sendMessageUsingThymeleafTemplate(String to, Map<String, Object> templateModel, String template, String subjectMessage) {

        Locale locale = LocaleContextHolder.getLocale();
        Context thymeleafContext = new Context(locale);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);
        String subject = messageSource.getMessage(subjectMessage, null ,locale);

        try {
            sendHtmlMessage(to, htmlBody, subject);
        } catch (MessagingException mex) {
            LOGGER.warn("Could not send email to {} with template {}", to, template, mex);
        }
    }

    private void sendHtmlMessage(String to, String htmlBody, String subject) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom(String.format("%s <%s>", SENDER_NAME, ADDRESS));
        helper.setSubject(subject);
        String plainTextBody = htmlBody.replaceAll("((<script>[^<]*</script>)|(<style>[^<]*</style>))|(<[^>]*>)", "");  // As per: https://www.baeldung.com/java-remove-html-tags this could bring some issues but for our uses it is enough.
        helper.setText(plainTextBody, htmlBody);
        emailSender.send(message);
    }
}