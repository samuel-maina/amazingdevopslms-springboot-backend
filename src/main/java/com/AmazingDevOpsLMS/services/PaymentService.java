package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.exceptions.ResourceNotFoundException;
import com.AmazingDevOpsLMS.exceptions.StudentNotFoundException;
import com.AmazingDevOpsLMS.model.Payments;
import com.AmazingDevOpsLMS.model.Program;
import com.AmazingDevOpsLMS.model.Session;
import com.AmazingDevOpsLMS.model.StripeClient;
import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.repositories.PaymentRepository;
import com.AmazingDevOpsLMS.repositories.SessionRepository;
import com.AmazingDevOpsLMS.repositories.StudentRepository;
import com.stripe.model.Charge;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private StripeClient stripeClient;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProgramService programService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    PaymentService(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    public Charge save(Principal principal, String programId, String token) throws Exception {
        Student student = studentRepository.findById(principal.getName()).orElseThrow(() -> new StudentNotFoundException(""));
        Program program = programService.findProgramById(programId);
        Charge r = this.stripeClient.chargeNewCard(token, program.getCost());
        if (r.getPaid() == true) {
            Payments payment = new Payments();
            payment.setId(r.getId());
            payment.setAmount(r.getAmount().intValue());
            payment.setPaymentDate(LocalDate.now());
            payment.setProgram(program);
            payment.setStudent(student);
            Payments temp = paymentRepository.save(payment);
            notificationService.save(temp);
            this.sendRegistrationVerificationMessage(student, program);
        }
        return r;
    }

    public List<Payments> getPaymentsByStudentId(Principal principal) {
        return paymentRepository.getPaymentsByStudentId(principal.getName());
    }

    public int getTotalPaymentsByStudentId(Principal principal) {
        return paymentRepository.getTotalPaymentsByStudentId(principal.getName());
    }

    public List<Program> getregisteredProgramsByStudentId(Principal principal) {
        return paymentRepository.getRegisteredProgramsByStudentId(principal.getName());
    }

    public Payments findById(Principal principal, String Id) {
        return paymentRepository.findByIdAndUser(Id, principal.getName());
    }

    public int sum() {
        return paymentRepository.getTotalPayments();
    }

    public List<Map<Integer, String>> getTotalPaymentsByMonth() {
        return paymentRepository.getTotalPaymentsByMonth();
    }

    public List<Payments> getAllPaymentsByMonth(int month, int year) {
        return paymentRepository.getAllPaymentsByMonth(month, year);
    }

    public List<String> paymentyears() {
        return paymentRepository.paymentYears();
    }

    public List<String> paymentMonthsByYear() {
        return paymentRepository.paymentMonthByYear();
    }

    public List<Student> getEnrollmentByMonth(int month, int year, String program) {
        return paymentRepository.getEnrollmentByMonth(month, year, program);
    }

    public List<Map<Integer, String>> getEnrollmentByProgramAndYear(int year) {
        return paymentRepository.getEnrollmentByProgramAndYear(year);
    }

    public List<Map<Integer, String>> getEnrollmentGroupedbyYear() {
        return paymentRepository.getEnrollmentGroupedbyYear();
    }

    public void sendRegistrationVerificationMessage(Student student, Program program) {

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
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // -- Create a new message --
            String subject = "Program registered successfully";
            String content = "<body style='width:300px; margin:20px; padding:20px;'>Dear [[name]],<br>"
                    + "<p>You have successfully registered for the <span style='font-weight:bold'> [[PROGRAM]] </span> program</p><br>"
                    + "Thank you,<br>"
                    + "AmazingDevOps Inc.</body>";
            content = content.replace("[[name]]", student.getFirstname());

            content = content.replace("[[PROGRAM]]", program.getName());
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

    public Map<String, List<Payments>> sortPaymentsByDate(Principal principal) {
        List<Payments> payments = paymentRepository.getPaymentsByStudentId(principal.getName());
        List<Payments> yesterday = new ArrayList();
        List<Payments> today = new ArrayList();
        List<Payments> older = new ArrayList();
        
        for (Payments p : payments) {
            System.out.println((ChronoUnit.DAYS.between(p.getPaymentDate(),LocalDate.now())));
            if (((ChronoUnit.DAYS.between(LocalDate.now(), p.getPaymentDate())) ) ==0) {
                
                today.add(p);
            }
            if (((ChronoUnit.DAYS.between(p.getPaymentDate(),LocalDate.now())) ) == 1) {
                yesterday.add(p);
            }
            if ((Math.abs(ChronoUnit.DAYS.between(p.getPaymentDate(),LocalDate.now()))) > 1) {
                p.setAge(String.valueOf(ChronoUnit.DAYS.between(p.getPaymentDate(),LocalDate.now())));
                older.add(p);
            }
        }
        Map<String, List<Payments>> sortedPayments = new HashMap<>();
        if (today.size() > 0) {
            sortedPayments.put("Today", today);
        }
        if (yesterday.size() > 0) {
            sortedPayments.put("Yesterday", yesterday);
        }
        if (older.size() > 0) {
            sortedPayments.put("Older", older);
        }

        return sortedPayments;
    }
}
