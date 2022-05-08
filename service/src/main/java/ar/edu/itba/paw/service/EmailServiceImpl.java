package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.PasswordResetToken;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;


    @Override
    public void sendReservationToRestaurant(long reservation_id, String to, String user, int amount, LocalDateTime when, String comments) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dineout.graphene@gmail.com");
        message.setTo(to);
        message.setSubject("Reserva " + reservation_id);

        String body = "¡Recibiste una reserva! \n" +
                "Numero de reserva: " + reservation_id + '\n' +
                "Mail: " + user + '\n' +
                "Para: " + when + '\n' +
                "Cantidad: " + amount + '\n' +
                "Comentarios: " + amount + '\n';

        message.setText(body);
        emailSender.send(message);
    }

    @Override
    public void sendPasswordResetTokenEmail(String contextPath, PasswordResetToken passwordResetToken, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dineout.graphene@gmail.com");
        message.setTo(user.getUsername());
        message.setSubject("recupera tu contraseña");

        String body = "hace click en el siguiente link para recuperar tu contraseña " + '\n' + contextPath + "/change_password?token=" + passwordResetToken.getToken() + '\n';

        message.setText(body);
        emailSender.send(message);
    }
}