
package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Notification;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface NotificationRepository extends CrudRepository<Notification, String>{
    
}
