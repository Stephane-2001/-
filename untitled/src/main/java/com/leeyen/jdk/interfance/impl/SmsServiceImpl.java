package com.leeyen.jdk.interfance.impl;

import com.leeyen.jdk.interfance.SmsService;

public class SmsServiceImpl implements SmsService {
    @Override
    public String sendMessage(String message) {

        System.out.println("send message: " + message);

        return message;
    }
}
