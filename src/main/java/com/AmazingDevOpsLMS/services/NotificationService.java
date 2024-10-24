package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.model.Blog;
import com.AmazingDevOpsLMS.model.Course;
import com.AmazingDevOpsLMS.model.Notification;
import com.AmazingDevOpsLMS.model.Payments;
import com.AmazingDevOpsLMS.model.Program;
import com.AmazingDevOpsLMS.model.Student;
import com.AmazingDevOpsLMS.repositories.NotificationRepository;
import java.time.LocalDate;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void save(Student student) {
        Notification notif = new Notification();
        notif.setDate(LocalDate.now());
        notif.setId(RandomString.make(64));
        notif.setMessage("A new student registration with name and id " + student.getFirstname() + " " + student.getLastname() + " " + student.getEmail());
        notificationRepository.save(notif);
    }

    public Iterable<Notification> notifications() {
        return notificationRepository.findAll();
    }

    public void save(Blog blog) {
        Notification notif = new Notification();
        notif.setDate(LocalDate.now());
        notif.setId(RandomString.make(64));
        notif.setMessage("A new article with title -" + blog.getTitle() + "- has been published");
        notificationRepository.save(notif);
    }
    
        public void save(Program program) {
        Notification notif = new Notification();
        notif.setDate(LocalDate.now());
        notif.setId(RandomString.make(64));
        notif.setMessage("A new program with name -" + program.getName() + "  has been created");
        notificationRepository.save(notif);
    }
        
           public void save(Course course) {
        Notification notif = new Notification();
        notif.setDate(LocalDate.now());
        notif.setId(RandomString.make(64));
        notif.setMessage("A new  course with name -" + course.getName() + " - has been created");
        notificationRepository.save(notif);
    }
                   public void save(Payments payment) {
        Notification notif = new Notification();
        notif.setDate(LocalDate.now());
        notif.setId(RandomString.make(64));
        notif.setMessage("A new  payment with ID -" + payment.getId() + " - has been made by " +payment.getStudent().getEmail());
        notificationRepository.save(notif);
    }   
           

}
