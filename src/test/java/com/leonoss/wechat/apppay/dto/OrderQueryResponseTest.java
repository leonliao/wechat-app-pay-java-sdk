package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.util.Util;

public class OrderQueryResponseTest {
  String key = "63d8ddd6ff234233edefb2633c218f7c";

  /**
   * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
   * 
   * <pre>
#1.生成字符串：
appid=wx333386e333333c44&attach=说明&bank_type=bank_type&cash_fee=10&cash_fee_type=CNY&coupon_count=2&coupon_fee=20&coupon_fee_1=10&coupon_fee_2=10&coupon_id_1=c1&coupon_id_2=c2&device_info=013467007045764&err_code=err_code&err_code_des=err_code_des&fee_type=CNY&is_subscribe=Y&mch_id=1611111110&nonce_str=nounce&openid=openid&out_trade_no=1217752501201407033233368018&result_code=SUCCESS&return_code=return_code&return_msg=return_msg&time_end=20091227091010&total_fee=888&trade_state=trade_state&trade_state_desc=trade_state_desc&trade_type=APP&transaction_id=transaction_id

#2.连接商户key：
appid=wx333386e333333c44&attach=说明&bank_type=bank_type&cash_fee=10&cash_fee_type=CNY&coupon_count=2&coupon_fee=20&coupon_fee_1=10&coupon_fee_2=10&coupon_id_1=c1&coupon_id_2=c2&device_info=013467007045764&err_code=err_code&err_code_des=err_code_des&fee_type=CNY&is_subscribe=Y&mch_id=1611111110&nonce_str=nounce&openid=openid&out_trade_no=1217752501201407033233368018&result_code=SUCCESS&return_code=return_code&return_msg=return_msg&time_end=20091227091010&total_fee=888&trade_state=trade_state&trade_state_desc=trade_state_desc&trade_type=APP&transaction_id=transaction_id&key=63d8ddd6ff234233edefb2633c218f7c

#3.md5编码并转成大写：
sign=D31B9AA7F55A3DCA94D8C5E774020202

#4.最终的提交xml：
<xml>
  <appid>wx333386e333333c44</appid>
  <attach>说明</attach>
  <bank_type>bank_type</bank_type>
  <cash_fee>10</cash_fee>
  <cash_fee_type>CNY</cash_fee_type>
  <coupon_count>2</coupon_count>
  <coupon_fee>20</coupon_fee>
  <coupon_fee_1>10</coupon_fee_1>
  <coupon_fee_2>10</coupon_fee_2>
  <coupon_id_1>c1</coupon_id_1>
  <coupon_id_2>c2</coupon_id_2>
  <device_info>013467007045764</device_info>
  <err_code>err_code</err_code>
  <err_code_des>err_code_des</err_code_des>
  <fee_type>CNY</fee_type>
  <is_subscribe>Y</is_subscribe>
  <mch_id>1611111110</mch_id>
  <nonce_str>nounce</nonce_str>
  <openid>openid</openid>
  <out_trade_no>1217752501201407033233368018</out_trade_no>
  <result_code>SUCCESS</result_code>
  <return_code>return_code</return_code>
  <return_msg>return_msg</return_msg>
  <time_end>20091227091010</time_end>
  <total_fee>888</total_fee>
  <trade_state>trade_state</trade_state>
  <trade_state_desc>trade_state_desc</trade_state_desc>
  <trade_type>APP</trade_type>
  <transaction_id>transaction_id</transaction_id>
  <sign>D31B9AA7F55A3DCA94D8C5E774020202</sign>
</xml>
   * </pre>
   */
  @Test
  public void testOrderQueryResponseCases() {
    String device_info = "013467007045764";
    String attach = "说明";
    String out_trade_no = "1217752501201407033233368018";
    int total_fee = 888;
    String time_end = "20091227091010";
    String nounce_str = "nounce";
    String appid = "wx333386e333333c44";
    String mch_id = "1611111110";

    OrderQueryResponse orderQueryResponse = new OrderQueryResponse();
    orderQueryResponse.setAppid(appid).setAttach(attach);
    orderQueryResponse.setBank_type("bank_type");
    orderQueryResponse.setCash_fee(10);
    orderQueryResponse.setCash_fee_type("CNY");
    orderQueryResponse.setCoupon_count(2);
    orderQueryResponse.setCoupon_fee(20);
    orderQueryResponse.set("coupon_id_1", "c1");
    orderQueryResponse.set("coupon_id_2", "c2");
    orderQueryResponse.set("coupon_fee_1", 10);
    orderQueryResponse.set("coupon_fee_2", 10);
    orderQueryResponse.setDevice_info(device_info);
    orderQueryResponse.setErr_code("err_code");
    orderQueryResponse.setErr_code_des("err_code_des");
    orderQueryResponse.setFee_type("CNY");
    orderQueryResponse.setIs_subscribe("Y");
    orderQueryResponse.setMch_id(mch_id);
    orderQueryResponse.setNonce_str(nounce_str);
    orderQueryResponse.setOpenid("openid");
    orderQueryResponse.setOut_trade_no(out_trade_no);
    orderQueryResponse.setResult_code("SUCCESS");
    orderQueryResponse.setReturn_code("return_code");
    orderQueryResponse.setReturn_msg("return_msg");
    orderQueryResponse.setTime_endByDate(Util.fromWeixinDateFormat(time_end));
    orderQueryResponse.setTotal_fee(total_fee);
    orderQueryResponse.setTrade_type("APP").setTrade_state("trade_state")
        .setTrade_state_desc("trade_state_desc");
    orderQueryResponse.setTransaction_id("transaction_id");

    orderQueryResponse.setSign(Util
        .validateFieldsAndGenerateWxSignature(orderQueryResponse, key));
    System.out.println(orderQueryResponse.toString());

    assertEquals("D31B9AA7F55A3DCA94D8C5E774020202", orderQueryResponse.getSign());
    System.out.println(WechatAppPayProtocolHandler.marshalToXml(orderQueryResponse, key));

    try {
      orderQueryResponse.setAppid(null);

      Util.validateFieldsAndGenerateWxSignature(orderQueryResponse, key);
      fail("Should fail for missing mandatory values.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  @Test
  public void testUnmarshall() throws MalformedPduException {
    String receivedXml = "<xml>" + 
        " <appid>wx333386e333333c44</appid>" + 
        " <attach>说明</attach>" + 
        " <bank_type>bank_type</bank_type>" + 
        " <cash_fee>10</cash_fee>" + 
        " <cash_fee_type>CNY</cash_fee_type>" + 
        " <coupon_count>2</coupon_count>" + 
        " <coupon_fee>20</coupon_fee>" + 
        " <coupon_fee_1>10</coupon_fee_1>" + 
        " <coupon_fee_2>10</coupon_fee_2>" + 
        " <coupon_id_1>c1</coupon_id_1>" + 
        " <coupon_id_2>c2</coupon_id_2>" + 
        " <device_info>013467007045764</device_info>" + 
        " <err_code>err_code</err_code>" + 
        " <err_code_des>err_code_des</err_code_des>" + 
        " <fee_type>CNY</fee_type>" + 
        " <is_subscribe>Y</is_subscribe>" + 
        " <mch_id>1611111110</mch_id>" + 
        " <nonce_str>nounce</nonce_str>" + 
        " <openid>openid</openid>" + 
        " <out_trade_no>1217752501201407033233368018</out_trade_no>" + 
        " <result_code>SUCCESS</result_code>" + 
        " <return_code>return_code</return_code>" + 
        " <return_msg>return_msg</return_msg>" + 
        " <time_end>20091227091010</time_end>" + 
        " <total_fee>888</total_fee>" + 
        " <trade_state>trade_state</trade_state>" + 
        " <trade_state_desc>trade_state_desc</trade_state_desc>" + 
        " <trade_type>APP</trade_type>" + 
        " <transaction_id>transaction_id</transaction_id>" + 
        " <sign>D31B9AA7F55A3DCA94D8C5E774020202</sign>" +
        "</xml>";

    OrderQueryResponse response = WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(
        receivedXml, OrderQueryResponse.class, key);
    System.out.println(response);
  }

  @Test
  public void testUnmarshallWrongXml() {
    String receivedXml = "<xml>" + 
        " <appid>wx333386e333333c44</appid>" + 
        " <attach>说明</attach>" + 
        " <bank_type>bank_type</bank_type>" + 
        " <cash_fee>10</cash_fee>" + 
        " <cash_fee_type>CNY</cash_fee_type>" + 
        " <coupon_count>2</coupon_count>" + 
        " <coupon_fee>20</coupon_fee>" + 
        " <coupon_fee_1>10</coupon_fee_1>" + 
        " <coupon_fee_2>10</coupon_fee_2>" + 
        " <coupon_id_1>c1</coupon_id_1>" + 
        " <coupon_id_2>c2</coupon_id_2>" + 
        " <device_info>013467007045764</device_info>" + 
        " <err_code>err_code</err_code>" + 
        " <err_code_des>err_code_des</err_code_des>" + 
        " <fee_type>CNY</fee_type>" + 
        " <is_subscribe>Y</is_subscribe>" + 
        " <mch_id>1611111110</mch_id>" + 
        " <nonce_str>nounce<nonce_str>" + 
        " <openid>openid</openid>" + 
        " <out_trade_no>1217752501201407033233368018</out_trade_no>" + 
        " <result_code>SUCCESS</result_code>" + 
        " <return_code>return_code</return_code>" + 
        " <return_msg>return_msg</return_msg>" + 
        " <time_end>20091227091010</time_end>" + 
        " <total_fee>888</total_fee>" + 
        " <trade_state>trade_state</trade_state>" + 
        " <trade_state_desc>trade_state_desc</trade_state_desc>" + 
        " <trade_type>APP</trade_type>" + 
        " <transaction_id>transaction_id</transaction_id>" + 
        " <sign>D31B9AA7F55A3DCA94D8C5E774020202</sign>" +
        "</xml>";
    try {
      WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(receivedXml,
          OrderQueryResponse.class, key);
      fail("Must fail for exception");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}
