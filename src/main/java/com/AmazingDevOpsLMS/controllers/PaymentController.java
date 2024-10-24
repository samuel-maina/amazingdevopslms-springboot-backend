/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.model.Payments;
import com.AmazingDevOpsLMS.services.PaymentService;
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
@RequestMapping("/api/v1/payment/")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{Id}")
    public ResponseEntity<?> findById(Principal principal, @PathVariable String Id) {
        return new ResponseEntity<>(paymentService.findById(principal, Id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(Principal principal) {
        return new ResponseEntity<>(paymentService.getPaymentsByStudentId(principal), HttpStatus.OK);
    }

    @GetMapping("/user/total_by_student")
    public ResponseEntity<?> getTotalPaymentsByStudentId(Principal principal) {
        return new ResponseEntity<>(paymentService.getTotalPaymentsByStudentId(principal), HttpStatus.OK);
    }

}
