package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface ChatRepository extends CrudRepository<Chat,String> {
    @Query("select chat from Chat chat inner join Message message on chat.id = message.chat.id where message.chat.id=?1 order by message.date DESC")
    public Chat chatMessages(String id);
    
    @Query("select Distinct(chat) from Chat chat inner join Message message on chat.id = message.chat.id where message.receiver.email=?1 and chat.fromm !=?1")
    public Iterable<Chat> inbox(String email);
    @Query("select Distinct(chat) from Chat chat inner join Message message on chat.id = message.chat.id where message.sender.email=?1 and chat.fromm =?1")
    public Iterable<Chat> outbox(String email);
}
