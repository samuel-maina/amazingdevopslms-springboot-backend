package com.AmazingDevOpsLMS.services;

import com.AmazingDevOpsLMS.model.Chat;
import com.AmazingDevOpsLMS.model.Message;
import com.AmazingDevOpsLMS.model.MessageType;
import com.AmazingDevOpsLMS.model.User;
import com.AmazingDevOpsLMS.repositories.ChatRepository;
import com.AmazingDevOpsLMS.repositories.MessageRepository;
import com.AmazingDevOpsLMS.repositories.UserRepository;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    public void createChat(Chat chat,Principal principal) {
        chat.setId(RandomString.make(10));
        chat.setDate(new Date());
        chat.setFrom(principal.getName());
        chat.getMessage().get(0).setId(RandomString.make(10));
        Message message = chat.getMessage().get(0);
        message.setDate(new Date());
        User sender = userRepository.getUserByEmail(principal.getName()).get();
        User receiver = userRepository.getUserByEmail(message.getReceiver()).get();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setChat(chat);
        message.setMessageType(MessageType.SENDER);
        messageRepository.save(message);
    }

    public Iterable<Chat> inbox(Principal principal) {
        return chatRepository.inbox(principal.getName());
    }
    
     public Iterable<Chat> outbox(Principal principal) {
        return chatRepository.outbox(principal.getName());
    }

    public Chat chatMessages(String id) {
        return chatRepository.chatMessages(id);
    }

    public void respondMessages(Message message, String id, Principal principal) {
        Chat chat = chatRepository.findById(id).get();
        List<Message> messages = chat.getMessage();
        message.setDate(new Date());
        User receiver =userRepository.getUserByEmail( messages.get(0).getReceiver()).get();
        User sender = userRepository.getUserByEmail( messages.get(0).getSender()).get();
        if (principal.getName().equals(receiver.getEmail())) {
            message.setReceiver(receiver);
            message.setSender(sender);
            message.setMessageType(MessageType.SENDEE);
        } else {
            message.setReceiver(sender);
            message.setSender(receiver);
            message.setMessageType(MessageType.SENDER);
        }
        message.setId(RandomString.make(10));
        message.setChat(chat);
        messageRepository.save(message);
        
    }

}
