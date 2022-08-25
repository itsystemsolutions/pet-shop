package com.thesis.petshop.services.email;

/*
 * Created: renzb
 * Date: 8/16/2022
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaMailSenderImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("PET SHOP FORGOT PASSWORD CODE");
        msg.setText("Hello AdoptForm, \nYour new password is " + code + " please goto our system then type the code.");

        javaMailSender.send(msg);
    }

}
