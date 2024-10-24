package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.model.Course;
import com.AmazingDevOpsLMS.model.Session;
import com.AmazingDevOpsLMS.repositories.PaymentRepository;
import com.AmazingDevOpsLMS.repositories.SessionRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProgramService programService;

    public List<Session> getCourseSessionByProgramId(String Id, Principal principal) {
        List<Session> sessions = sessionRepository.getCourseSessionByProgramId(Id);

        return sessions;
    }

    public List<Course> getCourseSessions() {
        return sessionRepository.getCourseSessions();
    }

    public List<Session> getAllCourseSessions() {
        return sessionRepository.findAll();
    }

    public void registerStudentSession(Principal principal, Long sessionId) {

    }

    public void save(String program) {
        Session session = new Session();
        session.setProgram(programService.findProgramById(program));
        sessionRepository.save(session);
    }

}
