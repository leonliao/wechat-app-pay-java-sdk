package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.leonoss.wechat.apppay.dto.PaymentNotificationResponse;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.util.Util;

import org.junit.Test;

import java.io.ByteArrayInputStream;

public class PaymentNotificationResponseTest {

  @Test
  public void testUnifiedOrderResponse() {

    PaymentNotificationResponse response = new PaymentNotificationResponse();
    response.setReturn_code("SUCCESS");
    response.setReturn_msg("return_msg");

    System.out.println(response.toString());
    System.out.println(WechatAppPayProtocolHandler.marshalToXml(response));

    try {
      response.setReturn_code(null);
      Util.validateValueObject(response);
      fail("should throw exception");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testUnmarshall() throws Exception {
    String xml = "<xml>  <return_code><![CDATA[SUCCESS]]></return_code>  <return_msg><![CDATA[OK]]></return_msg></xml>";

    PaymentNotificationResponse response = WechatAppPayProtocolHandler.unmarshalFromXml(xml,
        PaymentNotificationResponse.class);
    assertEquals("SUCCESS", response.getReturn_code());

    ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    response = WechatAppPayProtocolHandler.unmarshalFromXml(bis, PaymentNotificationResponse.class);
    System.out.println(response);
    bis.close();

  }

}
