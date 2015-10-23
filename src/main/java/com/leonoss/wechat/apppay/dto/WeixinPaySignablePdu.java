package com.leonoss.wechat.apppay.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 微信支付协议数据包基类
 * 
 * @author leon
 *
 */
public abstract class WeixinPaySignablePdu implements WeixinPayPdu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011613066844770293L;

	// 用来生成签名
	@JsonIgnore
	private String secret;
	
	private String sign;

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@JsonIgnore
	public String getSecret() {
		return secret;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "WeixinPayPdu [secret=" + secret + ", sign=" + sign + "]";
	}
}
