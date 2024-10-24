package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Course;
import com.AmazingDevOpsLMS.model.Session;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author samuel
 */
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("Select distinct(s) from Session s where s.program.Id=?1")
    public List<Session> getCourseSessionByProgramId(String Id);

    @Query("Select distinct(s.courses) from Session s")
    public List<Course> getCourseSessions();


}
