package com.example.leetcall.service.impl;

import com.example.leetcall.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallService {

    private final TwilioConfig twilioConfig;

    public void makeCall(String toPhoneNumber) {
        String twiml =
                "<Response><Say voice='alice'>" +
                        "Hello! This is your LeetCode reminder. " +
                        "You have not solved any problem today. " +
                        "Open LeetCode and keep that streak alive!" +
                        "</Say></Response>";

        log.info("Initiating call from {} to {}", twilioConfig.getPhoneNumber(), toPhoneNumber);
        Call call = Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                new Twiml(twiml)
        ).create();
        log.info("Call SID: {}", call.getSid());
    }
}