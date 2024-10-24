package com.AmazingDevOpsLMS.controllers;
import com.AmazingDevOpsLMS.model.Chat;
import com.AmazingDevOpsLMS.model.Message;
import com.AmazingDevOpsLMS.services.ChatService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/messaging/")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Chat chat,Principal principal) {
        chatService.createChat(chat,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("outbox")
    public ResponseEntity<?> outbox(Principal principal) {

        return new ResponseEntity<>(chatService.outbox(principal), HttpStatus.OK);
    }
      @GetMapping("inbox")
    public ResponseEntity<?> inbox(Principal principal) {

        return new ResponseEntity<>(chatService.inbox(principal), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> chat(@PathVariable String id) {
        return new ResponseEntity<>(chatService.chatMessages(id), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> respond(@PathVariable String id, @RequestBody Message message, Principal principal) {
        chatService.respondMessages(message, id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
