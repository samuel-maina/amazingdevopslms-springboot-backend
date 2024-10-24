package com.AmazingDevOpsLMS.controllers;


import com.AmazingDevOpsLMS.model.Payments;
import com.AmazingDevOpsLMS.model.StripeClient;
import com.AmazingDevOpsLMS.services.PaymentService;
import com.stripe.model.Charge;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/payment")
public class PaymentGatewayController {

@Autowired
public PaymentService paymentService;

    @PostMapping("/charge/{programId}")
    public ResponseEntity<Charge> chargeCard(@RequestHeader(value = "token") String token, @RequestHeader(value = "amount") Double amount,Principal principal,@PathVariable String programId) throws Exception {
      return new ResponseEntity<>(paymentService.save( principal, programId,token),HttpStatus.OK);   
                
    }
}
