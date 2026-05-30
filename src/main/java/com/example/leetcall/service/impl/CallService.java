package com.example.leetcall.service.impl;

import com.example.leetcall.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {

    private final TwilioConfig twilioConfig;

    public void makeCall(String toPhoneNumber, String problemName) {
        String twiml = String.format(
                "<Response><Say voice='alice'>" +
                "Hello! This is your LeetCode reminder. " +
                "You have not submitted an accepted solution for %s today. " +
                "Good luck and keep coding!" +
                "</Say></Response>",
                problemName
        );

        Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                new Twiml(twiml)
        ).create();
    }
}