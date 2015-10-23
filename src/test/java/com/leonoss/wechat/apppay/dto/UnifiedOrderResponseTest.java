package com.leonoss.wechat.apppay.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.leonoss.wechat.apppay.dto.UnifiedOrderResponse;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.util.Util;

public class UnifiedOrderResponseTest {
	String secret = "63d8ddd6ff234233edefb2633c218f7c";

	/**
	 * 本测试用例将程序输出与https://pay.weixin.qq.com/wiki/tools/signverify/的输出相比较
	 * 
	 * #1.生成字符串：
	 * appid=wx333386e333333c44&code_url=code_url&device_info=013467007045764
	 * &err_code
	 * =err_code&err_code_des=err_code_des&mch_id=1611111110&nonce_str=nounce
	 * &prepay_id
	 * =prepay_id&result_code=SUCCESS&return_code=return_code&return_msg
	 * =return_msg&trade_type=APP
	 * 
	 * #2.连接商户key：
	 * appid=wx333386e333333c44&code_url=code_url&device_info=013467007045764
	 * &err_code
	 * =err_code&err_code_des=err_code_des&mch_id=1611111110&nonce_str=nounce
	 * &prepay_id
	 * =prepay_id&result_code=SUCCESS&return_code=return_code&return_msg
	 * =return_msg&trade_type=APP&key=63d8ddd6ff234233edefb2633c218f7c
	 * 
	 * #3.md5编码并转成大写： sign=F6272455684CE7D610C3A7594AE194E5
	 * 
	 * #4.最终的提交xml： <xml> <appid>wx333386e333333c44</appid>
	 * <code_url>code_url</code_url> <device_info>013467007045764</device_info>
	 * <err_code>err_code</err_code> <err_code_des>err_code_des</err_code_des>
	 * <mch_id>1611111110</mch_id> <nonce_str>nounce</nonce_str>
	 * <prepay_id>prepay_id</prepay_id> <result_code>SUCCESS</result_code>
	 * <return_code>return_code</return_code>
	 * <return_msg>return_msg</return_msg> <trade_type>APP</trade_type>
	 * <sign>F6272455684CE7D610C3A7594AE194E5</sign> </xml>
	 * 
	 * 
	 */
	@Test
	public void testUnifiedOrderResponse() {

		String device_info = "013467007045764";
		String nounce_str = "nounce";
		String appid = "wx333386e333333c44";
		String mch_id = "1611111110";

		UnifiedOrderResponse response = new UnifiedOrderResponse();
		response.setAppid(appid);
		response.setCode_url("code_url");
		response.setDevice_info(device_info);
		response.setErr_code("err_code");
		response.setErr_code_des("err_code_des");
		response.setMch_id(mch_id);
		response.setNonce_str(nounce_str);
		response.setPrepay_id("prepay_id");
		response.setResult_code("SUCCESS");
		response.setReturn_code("return_code");
		response.setReturn_msg("return_msg");
		response.setTrade_type("APP");

		// 设置商户Key
		response.setSecret(secret);
		response.setSign(Util.validateFieldsAndGenerateWxSignature(response));
		System.out.println(response.toString());

		assertEquals("F6272455684CE7D610C3A7594AE194E5", response.getSign());
		System.out.println(WechatAppPayProtocolHandler.marshalToXml(response));

		response.setNonce_str("");
		try {
			Util.validateFieldsAndGenerateWxSignature(response);
			fail("should throw exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testUnmarshall() throws Exception {
		String xml = "<xml>" + "	<appid>wx333386e333333c44</appid>"
				+ "	<code_url>code_url</code_url>"
				+ "	<device_info>013467007045764</device_info>"
				+ "	<err_code>err_code</err_code>"
				+ "	<err_code_des>err_code_des</err_code_des>"
				+ "	<mch_id>1611111110</mch_id>"
				+ "	<nonce_str>nounce</nonce_str>"
				+ "	<prepay_id>prepay_id</prepay_id>"
				+ "	<result_code>SUCCESS</result_code>"
				+ "	<return_code>return_code</return_code>"
				+ "	<return_msg>return_msg</return_msg>"
				+ "	<trade_type>APP</trade_type>"
				+ "	<sign>F6272455684CE7D610C3A7594AE194E5</sign>" +

				"</xml>";
		UnifiedOrderResponse response = WechatAppPayProtocolHandler.unmarshalFromXml(
				xml, UnifiedOrderResponse.class, secret);

		ByteArrayInputStream bis = new ByteArrayInputStream(
				xml.getBytes("UTF-8"));
		response = WechatAppPayProtocolHandler.unmarshalFromXml(bis,
				UnifiedOrderResponse.class, secret);
		System.out.println(response);
		bis.close();

	}

}
