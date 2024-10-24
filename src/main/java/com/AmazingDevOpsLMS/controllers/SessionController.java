/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.model.Session;
import com.AmazingDevOpsLMS.services.PaymentService;
import com.AmazingDevOpsLMS.services.SessionService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("api/v1/sessions/")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("program/{programId}")
    public ResponseEntity<?> getCourseSessionByProgramId(@PathVariable String programId, Principal principal) {
        return new ResponseEntity<>(sessionService.getCourseSessionByProgramId(programId, principal), HttpStatus.OK);
    }

    @DeleteMapping("/student/registered/{sessionId}")
    public ResponseEntity<?> dropStudentCourse(@PathVariable Long sessionId, Principal principal) {
        //sessionService.dropStudentCourseSessions(principal, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourseSessions() {
        return new ResponseEntity<>(sessionService.getAllCourseSessions(), HttpStatus.OK);
    }

    @GetMapping("course/{courseID}")
    public ResponseEntity<?> getCourseSessionByCourseID(@PathVariable String courseID) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{sessionId}")
    public ResponseEntity<?> saveStudentCourse(@PathVariable Long sessionId, Principal principal) {
        sessionService.registerStudentSession(principal, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("student")
    public ResponseEntity<?> getRegisteredProgramsByStudentId(Principal principal) {
        return new ResponseEntity<>(paymentService.getregisteredProgramsByStudentId(principal), HttpStatus.OK);
    }

    @PostMapping("/program/{program}")
    public ResponseEntity<?> save(@PathVariable String program) {
        sessionService.save(program);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("sorted")
    public ResponseEntity<?> getSortedRegisteredProgramsByStudentId(Principal principal) {
        return new ResponseEntity<>(paymentService.sortPaymentsByDate(principal), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotalPaymentsByStudentId(Principal principal) {
        return new ResponseEntity<>(paymentService.getTotalPaymentsByStudentId(principal), HttpStatus.OK);
    }

}
