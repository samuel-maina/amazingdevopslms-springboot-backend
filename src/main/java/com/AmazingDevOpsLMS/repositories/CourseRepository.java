package com.AmazingDevOpsLMS.repositories;
import com.AmazingDevOpsLMS.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author samuel
 */
public interface CourseRepository extends PagingAndSortingRepository<Course,String>{

    @Query("from Course c where  c.session.id=?1")
    public List<Course> findCoursesBySessionId(long id);
     @Query("from Course c where  c.session.program.id=?1")
    public List<Course>findCoursesByProgramId(String id);
    
}
