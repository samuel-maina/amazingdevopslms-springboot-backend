/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.controllers;

import com.AmazingDevOpsLMS.services.PaymentService;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/enrollment/")
public class EnrollmentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("{year}/{month}/{program}")
    public ResponseEntity<?> getAllPaymentsByMonth(@PathVariable int month, @PathVariable int year, @PathVariable String program) {
        return new ResponseEntity<>(paymentService.getEnrollmentByMonth(month, year, program), HttpStatus.OK);
    }

    @GetMapping("{year}/enrollment")
    public ResponseEntity<?> getEnrollmentByProgramAndYear(@PathVariable int year) {
        return new ResponseEntity<>(paymentService.getEnrollmentByProgramAndYear(year), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getEnrollmentGroupedbyYear(){
        return new ResponseEntity<>(paymentService.getEnrollmentGroupedbyYear(), HttpStatus.OK);
    }

}
