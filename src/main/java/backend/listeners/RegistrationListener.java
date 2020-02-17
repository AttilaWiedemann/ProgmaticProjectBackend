package backend.listeners;

import backend.events.OnRegistrationCompleteEvent;
import backend.services.emailServices.TokenService;
import backend.services.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(event.getUser(), token);
        sendMail(event, token);
    }

    private void sendMail(OnRegistrationCompleteEvent event, String token){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(event.getUser().getEmail());
        email.setSubject("Registration Confirmation");
        email.setText("https://intense-meadow-41798.herokuapp.com" + event.getAppUrl() + "/regitrationConfirm?token=" + token);
        mailSender.send(email);
    }
}
