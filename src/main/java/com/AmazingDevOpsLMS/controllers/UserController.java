package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.model.Roles;
import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.model.User;
import com.AmazingDevOpsLMS.model.VerificationToken;
import com.AmazingDevOpsLMS.services.StudentService;
import com.AmazingDevOpsLMS.services.UserService;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users/")
public class UserController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MessageSource messages;

    @GetMapping
    public ResponseEntity<?> viewUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> profileImage(Principal principal) {
        return new ResponseEntity<>(userService.findUserById(principal.getName()).getImage().bigImage, HttpStatus.OK);
    }

    @PostMapping("/students/signup")
    public ResponseEntity<?> saveStudent(@RequestBody Student student, HttpServletRequest request) {
        String password = student.getPassword();
        student.setPassword(encoder.encode(password));
        student.setEnabled(false);
        return new ResponseEntity<>(studentService.saveStudent(student, request), HttpStatus.OK);
    }

    @PutMapping("/single")
    public ResponseEntity<?> updateStudent(@RequestBody User student, Principal principal) {
        return new ResponseEntity<>(userService.updateProfile(student, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/single")
    public ResponseEntity<?> loggedUser(Principal principal) {
        return new ResponseEntity<>(userService.findUserById(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/student/signup/verify/{token}")
    public ResponseEntity<?> confirmRegistration(WebRequest request, Model model, @PathVariable("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = studentService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "Invalid token";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Student student = verificationToken.getStudent();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = "Token has expired";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        User temp = userService.findUserById(student.getEmail());
        temp.setEnabled(true);
        userService.update(temp, temp.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/instructor/signup")
    public ResponseEntity<?> saveInstructor(@RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        user.setEnabled(false);
        return new ResponseEntity<>(userService.saveUser(user, Roles.INSTRUCTOR), HttpStatus.OK);
    }

    @PostMapping("/administrator/signup")
    public ResponseEntity<?> saveAdministrator(@RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        user.setEnabled(false);
        return new ResponseEntity<>(userService.saveUser(user, Roles.ADMINISTRATOR), HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody User user) {
        userService.activateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sendmail")
    public static void main(String[] args) {
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
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("codes@samuelmaina.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("james.academic1@gmail.com", true));
            msg.setSubject("Hello");
            msg.setText("How are you");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Erreur d'envoi, cause: " + e);
        }
    }

}
