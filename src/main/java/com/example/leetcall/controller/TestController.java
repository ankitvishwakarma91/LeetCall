package com.example.leetcall.controller;

import com.example.leetcall.service.impl.CallService;
import com.twilio.rest.api.v2010.account.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestController {


    @Value("${twilio.phone-number}")
    private String phoneNumber;

    private final CallService callService;

    TestController(CallService callService){
        this.callService = callService;
    }

    @GetMapping("/test-call")
    public ResponseEntity<String> testCall() {
        try {
            callService.makeCall(phoneNumber); // your verified number
            return ResponseEntity.ok("Call triggered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
