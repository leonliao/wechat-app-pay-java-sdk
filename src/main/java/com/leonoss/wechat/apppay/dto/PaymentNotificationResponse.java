package com.leonoss.wechat.apppay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_7&index=3 不需要签名
 * 
 * @author leon
 *
 */
@JacksonXmlRootElement(localName = "xml")
@JsonInclude(Include.NON_EMPTY)
public class PaymentNotificationResponse implements WeixinPayPdu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2254737846847521281L;
	// 返回状态码
	@MaxLength(16)
	@Required
	private String return_code;
	// 返回信息
	@MaxLength(128)
	private String return_msg;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	@Override
	public String toString() {
		return "PaymentNotificationResponse [return_code=" + return_code
				+ ", return_msg=" + return_msg + "]";
	}
}
