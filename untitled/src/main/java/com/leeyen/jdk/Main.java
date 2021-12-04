package com.leeyen.jdk;

import com.leeyen.jdk.factory.JdkProxyFactory;
import com.leeyen.jdk.interfance.SmsService;
import com.leeyen.jdk.interfance.impl.SmsServiceImpl;


public class Main {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.sendMessage("leeyen");
    }
}
