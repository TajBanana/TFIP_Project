package tajbanana.sudokuserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("ytahjian@gmail.com");

        msg.setSubject("Welcome to Sudoku");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }

}
