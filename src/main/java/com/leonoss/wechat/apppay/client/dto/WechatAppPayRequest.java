package com.leonoss.wechat.apppay.client.dto;

import com.leonoss.wechat.apppay.dto.MaxLength;
import com.leonoss.wechat.apppay.dto.Required;
import com.leonoss.wechat.apppay.dto.SignField;
import com.leonoss.wechat.apppay.dto.WeixinPaySignablePdu;

/**
 * 
 * 参照<a
 * href="https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=8_5">官方文档</a> 
 * <a
 * href="https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_12&index=2">官方文档2</a><br>
 * 所撰写的客户端所需信息。  因为只有服务器端保存key，所以所有客户端在请求微信调起支付时必须从服务器端获取所需信息。
 * 
 * @author leonliao
 *
 */

public class WechatAppPayRequest extends WeixinPaySignablePdu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6045775612152015920L;

	// 公众账号ID
	@MaxLength(32)
	@Required
	@SignField
	private String appid;
	// 商户号
	@MaxLength(32)
	@Required
	@SignField
	private String partnerid;
	// 随机字符串
	@MaxLength(32)
	@Required
	@SignField
	private String noncestr;
	// 业务结果, package是java关键字
	@MaxLength(128)
	@Required
	@SignField(signName="package")
	private String appPackage;
	// 错误代码
	@MaxLength(10)
	@SignField
	@Required
	private String timestamp;
	// 预支付交易会话标识
	@MaxLength(64)
	@Required
	@SignField
	private String prepayid;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getAppPackage() {
		return appPackage;
	}
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	@Override
	public String toString() {
		return "WechatAppRequest [appid=" + appid + ", partnerid=" + partnerid
				+ ", nonce_str=" + noncestr + ", appPackage=" + appPackage
				+ ", timestamp=" + timestamp + ", prepay_id=" + prepayid
				+ ", " + super.toString() + "]";
	}
}
