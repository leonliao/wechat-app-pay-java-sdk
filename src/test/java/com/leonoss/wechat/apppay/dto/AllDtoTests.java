package com.leonoss.wechat.apppay.dto;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderQueryResponseTest.class, OrderQueryTest.class,
    PaymentNotificationResponseTest.class, PaymentNotificationTest.class,
    UnifiedOrderResponseTest.class, UnifiedOrderTest.class })
public class AllDtoTests {

}
