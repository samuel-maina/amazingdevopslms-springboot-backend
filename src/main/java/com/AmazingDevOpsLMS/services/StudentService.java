package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.exceptions.EmailAlreadyRegisteredException;
import com.AmazingDevOpsLMS.exceptions.StudentNotFoundException;
import com.AmazingDevOpsLMS.model.Role;
import com.AmazingDevOpsLMS.model.Roles;
import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.model.UserRole;
import com.AmazingDevOpsLMS.model.VerificationToken;
import com.AmazingDevOpsLMS.repositories.RoleRepository;
import com.AmazingDevOpsLMS.repositories.StudentRepository;
import com.AmazingDevOpsLMS.repositories.UserRolesRepository;
import com.AmazingDevOpsLMS.repositories.VerificationTokenRepository;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class StudentService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private NotificationService notificationService;

    public Student findStudentById(String email) {
        return studentRepository.findById(email).orElseThrow(() -> new StudentNotFoundException(""));
    }

    public Student saveStudent(Student student, HttpServletRequest request) {

        Optional<Student> student_ = studentRepository.findById(student.getEmail());
        if (student_.isPresent()) {
            throw new EmailAlreadyRegisteredException("");
        }

        String code = RandomString.make(64);
        Student temp = studentRepository.save(student);
        notificationService.save(student);
        UserRole userRole = new UserRole();
        Role role_ = roleRepository.findById(Roles.STUDENT.ordinal()).get();
        userRole.setUser(temp);
        userRole.setRoles(role_);
        userRolesRepository.save(userRole);
        this.createVerificationToken(student, code);
        String appUrl = request.getContextPath();
        sendVerificationToken(student, code, appUrl);
        return temp;
    }

    public void createVerificationToken(Student student, String token) {
        VerificationToken myToken = new VerificationToken(token, student);
        Date date = new Date();
        Long l = date.getTime() + 5 * 24 * 60 * 10000;
        Date temp = new Date();
        temp.setTime(l);
        myToken.setExpiryDate(temp);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    private void sendVerificationEmail(Student student, String token, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = student.getEmail();
        String fromAddress = "codes@samuelmaina.com";
        String senderName = "AmazingDevops";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", student.getFirstname());
        String verifyURL = "http://samuelmaina.com/registration/verify?code=" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("Attempting to send mail");

    }

    public void sendVerificationToken(Student student, String token, String siteURL) {

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "samuelmaina.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "codes@samuelmaina.com";//
        final String password = "hi(P@0Ou*LRJ";
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // -- Create a new message --
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "AmazingDevOps Inc.";
            content = content.replace("[[name]]", student.getFirstname());
            String verifyURL = "http://samuelmaina.com/registration/verify/token/" + token;

            content = content.replace("[[URL]]", verifyURL);
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("codes@samuelmaina.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(student.getEmail(), true));
            msg.setSubject(subject);
            //msg.setText(content);
            msg.setContent(content, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Erreur d'envoi, cause: " + e);
        }

    }
}
