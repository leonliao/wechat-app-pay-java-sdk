package com.leonoss.wechat.apppay.client.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.leonoss.wechat.apppay.util.Util;

import org.junit.Test;


public class WechatAppPayRequestTest {
	String key = "63d8ddd6ff234233edefb2633c218f7c";

	/**
	 * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
	 * 
	 * #1.生成字符串：
appid=wx333386e333333c44&noncestr=nounce&package=package&partnerid=1611111110&prepayid=prepayid&timestamp=timestamp

#2.连接商户key：
appid=wx333386e333333c44&noncestr=nounce&package=package&partnerid=1611111110&prepayid=prepayid&timestamp=timestamp&key=63d8ddd6ff234233edefb2633c218f7c

#3.md5编码并转成大写：
sign=13D4F799B343103B09EFCCC8CD477CF5
	 * 
	 * 
	 */
	@Test
	public void testUnifiedOrderResponse() {
		String nounce_str = "nounce";
		String appid = "wx333386e333333c44";
		String mch_id = "1611111110";

		WechatAppPayRequest appPayRequest = new WechatAppPayRequest();
		appPayRequest.setAppid(appid);
		appPayRequest.setAppPackage("package");
		appPayRequest.setNoncestr(nounce_str);
		appPayRequest.setPartnerid(mch_id);
		appPayRequest.setPrepayid("prepayid");
		appPayRequest.setTimestamp("timestamp");

		// 设置商户Key
		appPayRequest.setSign(Util.validateFieldsAndGenerateWxSignature(appPayRequest, key));
		System.out.println(appPayRequest.toString());

		assertEquals("13D4F799B343103B09EFCCC8CD477CF5", appPayRequest.getSign());

		appPayRequest.setTimestamp("");
		try {
			Util.validateFieldsAndGenerateWxSignature(appPayRequest,key);
			fail("should throw exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
