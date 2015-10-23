package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.leonoss.wechat.apppay.dto.UnifiedOrder;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.util.Util;

public class UnifiedOrderTest {
	String secret = "63d8ddd6ff234233edefb2633c218f7c";


	/**
	 * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
	 * 
	 * 
	 * <pre>
	 * #1.生成字符串：
	 * appid=wx333386e333333c44&attach=说明&body=Ipad mini 16G 白色&detail=Ipad mini 16G 白色 二手&device_info=013467007045764&fee_type=CNY&goods_tag=WXG&limit_pay=limit_pay&mch_id=1611111110&nonce_str=nounce&notify_url=http://yoursite.com&out_trade_no=1217752501201407033233368018&product_id=12235413214070356458058&spbill_create_ip=8.8.8.8&time_expire=20091227091010&time_start=20091225091010&total_fee=888&trade_type=APP
	 * 
	 * #2.连接商户key：
	 * appid=wx333386e333333c44&attach=说明&body=Ipad mini 16G 白色&detail=Ipad mini 16G 白色 二手&device_info=013467007045764&fee_type=CNY&goods_tag=WXG&limit_pay=limit_pay&mch_id=1611111110&nonce_str=nounce&notify_url=http://yoursite.com&out_trade_no=1217752501201407033233368018&product_id=12235413214070356458058&spbill_create_ip=8.8.8.8&time_expire=20091227091010&time_start=20091225091010&total_fee=888&trade_type=APP&key=63d8ddd6ff234233edefb2633c218f7c
	 * 
	 * #3.md5编码并转成大写：
	 * sign=FCFAC3E09969AF7AF5423EF1BFB02F3C
	 * 
	 * #4.最终的提交xml：
	 * <xml>
	 * 	<appid>wx333386e333333c44</appid>
	 * 	<attach>说明</attach>
	 * 	<body>Ipad mini 16G 白色</body>
	 * 	<detail>Ipad mini 16G 白色 二手</detail>
	 * 	<device_info>013467007045764</device_info>
	 * 	<fee_type>CNY</fee_type>
	 * 	<goods_tag>WXG</goods_tag>
	 * 	<limit_pay>limit_pay</limit_pay>
	 * 	<mch_id>1611111110</mch_id>
	 * 	<nonce_str>nounce</nonce_str>
	 * 	<notify_url>http://yoursite.com</notify_url>
	 * 	<out_trade_no>1217752501201407033233368018</out_trade_no>
	 * 	<product_id>12235413214070356458058</product_id>
	 * 	<spbill_create_ip>8.8.8.8</spbill_create_ip>
	 * 	<time_expire>20091227091010</time_expire>
	 * 	<time_start>20091225091010</time_start>
	 * 	<total_fee>888</total_fee>
	 * 	<trade_type>APP</trade_type>
	 * 	<sign>FCFAC3E09969AF7AF5423EF1BFB02F3C</sign>
	 * </xml>
	 * </pre>
	 */
	@Test
	public void testUnifiedOrderCases() {

		String device_info = "013467007045764";
		String body = "Ipad mini 16G 白色";
		String detail = "Ipad mini 16G 白色 二手";
		String attach = "说明";
		String out_trade_no = "1217752501201407033233368018";
		int total_fee = 888;
		String spbill_create_ip = "8.8.8.8";
		String time_start = "20091225091010";
		String time_expire = "20091227091010";
		String goods_tag = "WXG";
		String notify_url = "http://yoursite.com";
		String product_id = "12235413214070356458058";
		String nounce_str = "nounce";
		String appid = "wx333386e333333c44";
		String mch_id = "1611111110";

		UnifiedOrder order = new UnifiedOrder();
		order.setAppid(appid).setMch_id(mch_id).setAttach(attach).setBody(body)
				.setDetail(detail).setDevice_info(device_info)
				.setGoods_tag(goods_tag).setLimit_pay("limit_pay")
				.setNonce_str(nounce_str);
		order.setNotify_url(notify_url);
		order.setOut_trade_no(out_trade_no);
		order.setProduct_id(product_id);
		order.setSpbill_create_ip(spbill_create_ip);
		order.setTime_startByDate(Util.fromWeixinDateFormat(time_start));
		order.setTime_expireByDate(Util.fromWeixinDateFormat(time_expire));
		order.setTotal_fee(total_fee);
		System.out.println(order.toString());
		//设置商户Key
		order.setSecret(secret);
		order.setSign(Util.validateFieldsAndGenerateWxSignature(order));
		System.out.println(order.toString());

		assertEquals("FCFAC3E09969AF7AF5423EF1BFB02F3C", order.getSign());
		System.out.println(WechatAppPayProtocolHandler.marshalToXml(order));
		
		// body maximum length is 32, set 4*9=36 bytes
		// exception expected.
		final String stringWith36Bytes = "123456789123456789123456789123456789";
		order.setBody(stringWith36Bytes);
		try {
			Util.validateFieldsAndGenerateWxSignature(order);
			fail("should throw exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//body is required, must throw exception
		order.setBody(null);
		try {
			Util.validateFieldsAndGenerateWxSignature(order);
			fail("should throw exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		order.setBody("");
		try {
			Util.validateFieldsAndGenerateWxSignature(order);
			fail("should throw exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	public void testUnmarshall() throws MalformedPduException {
		String xml =  "<xml>" + 
		"	  	<appid>wx333386e333333c44</appid>" + 
		"	  	<attach>说明</attach>" + 
		"	  	<body>Ipad mini 16G 白色</body>" + 
		"	  	<detail>Ipad mini 16G 白色 二手</detail>" + 
		"	  	<device_info>013467007045764</device_info>" + 
		"	  	<fee_type>CNY</fee_type>" + 
		"	 	<goods_tag>WXG</goods_tag>" + 
		"	  	<limit_pay>limit_pay</limit_pay>" + 
		"	  	<mch_id>1611111110</mch_id>" + 
		"	  	<nonce_str>nounce</nonce_str>" + 
		"	  	<notify_url>http://yoursite.com</notify_url>" + 
		"	  	<out_trade_no>1217752501201407033233368018</out_trade_no>" + 
		"	  	<product_id>12235413214070356458058</product_id>" + 
		"	  	<spbill_create_ip>8.8.8.8</spbill_create_ip>" + 
		"	  	<time_expire>20091227091010</time_expire>" + 
		"	  	<time_start>20091225091010</time_start>" + 
		"	  	<total_fee>888</total_fee>" + 
		"	  	<trade_type>APP</trade_type>" + 
		"	  	<sign>FCFAC3E09969AF7AF5423EF1BFB02F3C</sign></xml>";
		
		UnifiedOrder order = WechatAppPayProtocolHandler
				.unmarshalFromXml(xml, UnifiedOrder.class,
						secret);
		System.out.println(order);

	}

}
