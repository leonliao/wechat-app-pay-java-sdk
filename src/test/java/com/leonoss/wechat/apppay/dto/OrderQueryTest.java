package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.leonoss.wechat.apppay.util.Util;

public class OrderQueryTest {
  String appKey = "63d8ddd6ff234233edefb2633c218f7c";

  /**
   * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
   * 
   * 
   * 
   */
  @Test
  public void testOrderQueryResponse() {
    OrderQuery query = new OrderQuery();
    query.setAppid("appid");
    query.setMch_id("mch_id");
    query.setNonce_str("nonce_str").setOut_trade_no("out_trade_no")
        .setTransaction_id("transaction_id");

    // 设置商户Key
    query.setSign(Util.validateFieldsAndGenerateWxSignature(query, appKey));
    System.out.println(query.toString());

    assertEquals("DE864781DE2A540FDF4621A8A6E824DB", query.getSign());
    System.out.println(WechatAppPayProtocolHandler.marshalToXml(query, appKey));
  }

  @Test
  public void testUnmarshall() throws Exception {

    String xml = "<xml>" + " <appid>appid</appid>" + " <mch_id>mch_id</mch_id>"
        + " <nonce_str>nonce_str</nonce_str>" + " <out_trade_no>out_trade_no</out_trade_no>"
        + " <transaction_id>transaction_id</transaction_id>"
        + " <sign>DE864781DE2A540FDF4621A8A6E824DB</sign>" +

        "</xml>";
    OrderQuery query = WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(xml,
        OrderQuery.class, appKey);

    ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    query = WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(bis, OrderQuery.class,
        appKey);
    System.out.println(query);
    bis.close();

  }

}
