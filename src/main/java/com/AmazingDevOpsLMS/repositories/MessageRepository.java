package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Message;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface MessageRepository extends CrudRepository<Message,String> {
    
}
