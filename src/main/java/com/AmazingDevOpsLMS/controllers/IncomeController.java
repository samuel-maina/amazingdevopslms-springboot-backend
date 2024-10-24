/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.services.PaymentService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@RequestMapping("/api/v1/income/")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> sum() {
        return new ResponseEntity<>(paymentService.sum(), HttpStatus.OK);
    }

    @GetMapping("groupings")
    public ResponseEntity<?>groupByMonth() {
        return new ResponseEntity<>(paymentService.getTotalPaymentsByMonth(), HttpStatus.OK);
    }

    @GetMapping("{year}/{month}")
    public ResponseEntity<?> getAllPaymentsByMonth(@PathVariable int month, @PathVariable int year) {
        return new ResponseEntity<>(paymentService.getAllPaymentsByMonth(month,year), HttpStatus.OK);
    }
    @GetMapping("years")
    public ResponseEntity<?> getAllPaymentYears() {
        return new ResponseEntity<>(paymentService.paymentyears(), HttpStatus.OK);
    }
     @GetMapping("months")
    public ResponseEntity<?> getAllPaymentMonthsByYear() {
        return new ResponseEntity<>(paymentService.paymentMonthsByYear(), HttpStatus.OK);
    }
}
