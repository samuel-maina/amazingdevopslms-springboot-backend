package com.AmazingDevOpsLMS.repositories;
import com.AmazingDevOpsLMS.model.Blog;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface BlogRepository extends CrudRepository<Blog, String>{
    
}
