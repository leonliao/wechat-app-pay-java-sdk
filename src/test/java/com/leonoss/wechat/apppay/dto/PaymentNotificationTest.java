package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.leonoss.wechat.apppay.dto.PaymentNotification;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.util.Util;

public class PaymentNotificationTest {
	String secret = "63d8ddd6ff234233edefb2633c218f7c";

	/**
	 * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
	 * 
	 * <pre>
	 * 	 #1.生成字符串：
	 * appid=wx333386e333333c44&attach=说明&bank_type=bank_type&cash_fee=10&cash_fee_type=CNY&coupon_count=2&coupon_fee=20&coupon_fee_1=10&coupon_fee_2=10&coupon_id_1=c1&coupon_id_2=c2&device_info=013467007045764&err_code=err_code&err_code_des=err_code_des&fee_type=CNY&is_subscribe=Y&mch_id=1611111110&nonce_str=nounce&openid=openid&out_trade_no=1217752501201407033233368018&result_code=SUCCESS&return_code=return_code&return_msg=return_msg&time_end=20091227091010&total_fee=888&trade_type=APP&transaction_id=transaction_id
	 * 
	 * #2.连接商户key：
	 * appid=wx333386e333333c44&attach=说明&bank_type=bank_type&cash_fee=10&cash_fee_type=CNY&coupon_count=2&coupon_fee=20&coupon_fee_1=10&coupon_fee_2=10&coupon_id_1=c1&coupon_id_2=c2&device_info=013467007045764&err_code=err_code&err_code_des=err_code_des&fee_type=CNY&is_subscribe=Y&mch_id=1611111110&nonce_str=nounce&openid=openid&out_trade_no=1217752501201407033233368018&result_code=SUCCESS&return_code=return_code&return_msg=return_msg&time_end=20091227091010&total_fee=888&trade_type=APP&transaction_id=transaction_id&key=63d8ddd6ff234233edefb2633c218f7c
	 * 
	 * #3.md5编码并转成大写：
	 * sign=9973E73A7F4EF10536C7FBBA79EF673E
	 * 
	 * #4.最终的提交xml：
	 * <xml>
	 * 	<appid>wx333386e333333c44</appid>
	 * 	<attach>说明</attach>
	 * 	<bank_type>bank_type</bank_type>
	 * 	<cash_fee>10</cash_fee>
	 * 	<cash_fee_type>CNY</cash_fee_type>
	 * 	<coupon_count>2</coupon_count>
	 * 	<coupon_fee>20</coupon_fee>
	 * 	<coupon_fee_1>10</coupon_fee_1>
	 * 	<coupon_fee_2>10</coupon_fee_2>
	 * 	<coupon_id_1>c1</coupon_id_1>
	 * 	<coupon_id_2>c2</coupon_id_2>
	 * 	<device_info>013467007045764</device_info>
	 * 	<err_code>err_code</err_code>
	 * 	<err_code_des>err_code_des</err_code_des>
	 * 	<fee_type>CNY</fee_type>
	 * 	<is_subscribe>Y</is_subscribe>
	 * 	<mch_id>1611111110</mch_id>
	 * 	<nonce_str>nounce</nonce_str>
	 * 	<openid>openid</openid>
	 * 	<out_trade_no>1217752501201407033233368018</out_trade_no>
	 * 	<result_code>SUCCESS</result_code>
	 * 	<return_code>return_code</return_code>
	 * 	<return_msg>return_msg</return_msg>
	 * 	<time_end>20091227091010</time_end>
	 * 	<total_fee>888</total_fee>
	 * 	<trade_type>APP</trade_type>
	 * 	<transaction_id>transaction_id</transaction_id>
	 * 	<sign>9973E73A7F4EF10536C7FBBA79EF673E</sign>
	 * </xml>
	 * </pre>
	 */
	@Test
	public void testPaymentNotificationCases() {
		String device_info = "013467007045764";
		String attach = "说明";
		String out_trade_no = "1217752501201407033233368018";
		int total_fee = 888;
		String time_end = "20091227091010";
		String nounce_str = "nounce";
		String appid = "wx333386e333333c44";
		String mch_id = "1611111110";

		PaymentNotification notification = new PaymentNotification();
		notification.setAppid(appid).setAttach(attach);
		notification.setBank_type("bank_type");
		notification.setCash_fee(10);
		notification.setCash_fee_type("CNY");
		notification.setCoupon_count(2);
		notification.setCoupon_fee(20);
		notification.set("coupon_id_1", "c1");
		notification.set("coupon_id_2", "c2");
		notification.set("coupon_fee_1", 10);
		notification.set("coupon_fee_2", 10);
		notification.setDevice_info(device_info);
		notification.setErr_code("err_code");
		notification.setErr_code_des("err_code_des");
		notification.setFee_type("CNY");
		notification.setIs_subscribe("Y");
		notification.setMch_id(mch_id);
		notification.setNonce_str(nounce_str);
		notification.setOpenid("openid");
		notification.setOut_trade_no(out_trade_no);
		notification.setResult_code("SUCCESS");
		notification.setReturn_code("return_code");
		notification.setReturn_msg("return_msg");
		notification.setTime_endByDate(Util.fromWeixinDateFormat(time_end));
		notification.setTotal_fee(total_fee);
		notification.setTrade_type("APP");
		notification.setTransaction_id("transaction_id");

		notification.setSecret(secret);

		notification.setSign(Util
				.validateFieldsAndGenerateWxSignature(notification));
		System.out.println(notification.toString());

		assertEquals("9973E73A7F4EF10536C7FBBA79EF673E", notification.getSign());
		System.out.println(WechatAppPayProtocolHandler.marshalToXml(notification));

		try {
			notification.setAppid(null);

			Util.validateFieldsAndGenerateWxSignature(notification);
			fail("Should fail for missing mandatory values.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testUnmarshall() throws MalformedPduException {
		String receivedXml = "<xml>" + "	<appid>wx333386e333333c44</appid>"
				+ "	<attach>说明</attach>" + "	<bank_type>bank_type</bank_type>"
				+ "	<cash_fee>10</cash_fee>"
				+ "	<cash_fee_type>CNY</cash_fee_type>"
				+ "	<coupon_count>2</coupon_count>"
				+ "	<coupon_fee>20</coupon_fee>"
				+ "	<coupon_fee_1>10</coupon_fee_1>"
				+ "	<coupon_fee_2>10</coupon_fee_2>"
				+ "	<coupon_id_1>c1</coupon_id_1>"
				+ "	<coupon_id_2>c2</coupon_id_2>"
				+ "	<device_info>013467007045764</device_info>"
				+ "	<err_code>err_code</err_code>"
				+ "	<err_code_des>err_code_des</err_code_des>"
				+ "	<fee_type>CNY</fee_type>"
				+ "	<is_subscribe>Y</is_subscribe>"
				+ "	<mch_id>1611111110</mch_id>"
				+ "	<nonce_str>nounce</nonce_str>" + "	<openid>openid</openid>"
				+ "	<out_trade_no>1217752501201407033233368018</out_trade_no>"
				+ "	<result_code>SUCCESS</result_code>"
				+ "	<return_code>return_code</return_code>"
				+ "	<return_msg>return_msg</return_msg>"
				+ "	<time_end>20091227091010</time_end>"
				+ "	<total_fee>888</total_fee>"
				+ "	<trade_type>APP</trade_type>"
				+ "	<transaction_id>transaction_id</transaction_id>"
				+ "	<sign>9973E73A7F4EF10536C7FBBA79EF673E</sign></xml>";
		PaymentNotification notification = WechatAppPayProtocolHandler
				.unmarshalFromXml(receivedXml, PaymentNotification.class,
						secret);
		System.out.println(notification);
	}

	@Test
	public void testUnmarshallWrongXml() {
		String receivedXml = "<xml>"
				+ "	<appid>wx333386e333333c44</appid>"
				+ "	<attach>说明</attach>"
				+ "	<bank_type>bank_type</bank_type>"
				+ "	<cash_fee>10</cash_fee>"
				+ "	<cash_fee_type>CNY</cash_fee_type>"
				+ "	<coupon_count>2</coupon_count>"
				+ "	<coupon_fee>20</coupon_fee>"
				+ "	<coupon_fee_1>10</coupon_fee_3>" // wrong here, close tag
														// not matching
				+ "	<coupon_fee_2>10</coupon_fee_2>"
				+ "	<coupon_id_1>c1</coupon_id_1>"
				+ "	<coupon_id_2>c2</coupon_id_2>"
				+ "	<device_info>013467007045764</device_info>"
				+ "	<err_code>err_code</err_code>"
				+ "	<err_code_des>err_code_des</err_code_des>"
				+ "	<fee_type>CNY</fee_type>"
				+ "	<is_subscribe>Y</is_subscribe>"
				+ "	<mch_id>1611111110</mch_id>"
				+ "	<nonce_str>nounce</nonce_str>" + "	<openid>openid</openid>"
				+ "	<out_trade_no>1217752501201407033233368018</out_trade_no>"
				+ "	<result_code>SUCCESS</result_code>"
				+ "	<return_code>return_code</return_code>"
				+ "	<return_msg>return_msg</return_msg>"
				+ "	<time_end>20091227091010</time_end>"
				+ "	<total_fee>888</total_fee>"
				+ "	<trade_type>APP</trade_type>"
				+ "	<transaction_id>transaction_id</transaction_id>"
				+ "	<sign>9973E73A7F4EF10536C7FBBA79EF673E</sign></xml>";
		try {
			WechatAppPayProtocolHandler.unmarshalFromXml(receivedXml,
					PaymentNotification.class, secret);
			fail("Must fail for exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
