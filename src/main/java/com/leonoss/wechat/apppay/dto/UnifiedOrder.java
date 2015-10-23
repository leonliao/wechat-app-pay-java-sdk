package com.leonoss.wechat.apppay.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.leonoss.wechat.apppay.util.Util;

/**
 * 
 * 参照<a
 * href="https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1">官方文档</a> <br>
 * 所撰写的统一下单实体类
 * 
 * @author leonliao
 *
 */
@JacksonXmlRootElement(localName = "xml")
@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect
public class UnifiedOrder extends WeixinPaySignablePdu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4990302734672853683L;

	static final String FEE_TYPE = "CNY";
	static final String TRADE_TYPE = "APP";

	public UnifiedOrder() {
		this.fee_type = FEE_TYPE;
		this.trade_type = TRADE_TYPE;
	}

	// 公众账号ID
	@MaxLength(32)
	@Required
	@SignField
	private String appid;
	// 商户号
	@MaxLength(32)
	@Required
	@SignField
	private String mch_id;
	// 设备号
	@MaxLength(32)
	@SignField
	private String device_info;
	// 随机字符串
	@MaxLength(32)
	@Required
	@SignField
	private String nonce_str;
	// 商品描述
	@MaxLength(32)
	@Required
	@SignField
	private String body;
	// 商品详情
	@MaxLength(8192)
	@SignField
	private String detail;
	// 附加数据
	@MaxLength(127)
	@SignField
	private String attach;
	// 商户订单号
	@MaxLength(32)
	@Required
	@SignField
	private String out_trade_no;
	// 货币类型
	@MaxLength(16)
	@SignField
	private String fee_type;
	// 总金额
	@Required
	@SignField
	private Integer total_fee;

	// 终端IP
	@MaxLength(16)
	@Required
	@SignField
	private String spbill_create_ip;
	// 交易起始时间
	@MaxLength(14)
	@SignField
	private String time_start;
	// 交易结束时间订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则.注意：最短失效时间间隔必须大于5分钟;
	@MaxLength(14)
	@SignField
	private String time_expire;
	// 商品标记
	@MaxLength(32)
	@SignField
	private String goods_tag;
	// 通知地址
	@MaxLength(256)
	@Required
	@SignField
	private String notify_url;
	// 交易类型
	@MaxLength(16)
	@Required
	@SignField
	private String trade_type;
	// 商品ID
	@MaxLength(32)
	@SignField
	private String product_id;
	// 指定支付方式
	@MaxLength(32)
	@SignField
	private String limit_pay;
	// 用户标识
	@MaxLength(128)
	@SignField
	private String openid;

	public String getAppid() {
		return appid;
	}

	public UnifiedOrder setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public String getMch_id() {
		return mch_id;
	}

	public UnifiedOrder setMch_id(String mch_id) {
		this.mch_id = mch_id;
		return this;
	}

	public String getDevice_info() {
		return device_info;
	}

	public UnifiedOrder setDevice_info(String device_info) {
		this.device_info = device_info;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public UnifiedOrder setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
		return this;
	}

	public String getBody() {
		return body;
	}

	public UnifiedOrder setBody(String body) {
		this.body = body;
		return this;
	}

	public String getDetail() {
		return detail;
	}

	public UnifiedOrder setDetail(String detail) {
		this.detail = detail;
		return this;
	}

	public String getAttach() {
		return attach;
	}

	public UnifiedOrder setAttach(String attach) {
		this.attach = attach;
		return this;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public UnifiedOrder setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
		return this;
	}

	public String getFee_type() {
		return fee_type;
	}

	public UnifiedOrder setFee_type(String fee_type) {
		this.fee_type = fee_type;
		return this;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public UnifiedOrder setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
		return this;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public UnifiedOrder setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
		return this;
	}

	public String getTime_start() {
		return time_start;
	}

	public UnifiedOrder setTime_startByDate(Date time_start) {
		this.time_start = Util.toWeixinDateFormat(time_start);
		return this;
	}
	
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	@JsonIgnore
	public Date getTime_startAsDate() {
		return Util.fromWeixinDateFormat(time_start);
	}
	
	@JsonIgnore
	public Date getTime_expireAsDate() {
		return Util.fromWeixinDateFormat(time_expire);
	}
	
	public String getTime_expire() {
		return time_expire;
	}

	public UnifiedOrder setTime_expireByDate(Date time_expire) {
		this.time_expire = Util.toWeixinDateFormat(time_expire);
		return this;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public UnifiedOrder setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
		return this;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public UnifiedOrder setNotify_url(String notify_url) {
		this.notify_url = notify_url;
		return this;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public UnifiedOrder setTrade_type(String trade_type) {
		this.trade_type = trade_type;
		return this;
	}

	public String getProduct_id() {
		return product_id;
	}

	public UnifiedOrder setProduct_id(String product_id) {
		this.product_id = product_id;
		return this;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public UnifiedOrder setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
		return this;
	}

	public String getOpenid() {
		return openid;
	}

	public UnifiedOrder setOpenid(String openid) {
		this.openid = openid;
		return this;
	}

	@Override
	public String toString() {
		return "UnifiedOrder [appid=" + appid + ", mch_id=" + mch_id
				+ ", device_info=" + device_info + ", nonce_str=" + nonce_str
				+ ", body=" + body + ", detail=" + detail + ", attach="
				+ attach + ", out_trade_no=" + out_trade_no + ", fee_type="
				+ fee_type + ", total_fee=" + total_fee + ", spbill_create_ip="
				+ spbill_create_ip + ", time_start=" + time_start
				+ ", time_expire=" + time_expire + ", goods_tag=" + goods_tag
				+ ", notify_url=" + notify_url + ", trade_type=" + trade_type
				+ ", product_id=" + product_id + ", limit_pay=" + limit_pay
				+ ", openid=" + openid + "," + super.toString() + "]";
	}
}
