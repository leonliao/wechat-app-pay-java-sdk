package com.leonoss.wechat.apppay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * 参照<a href="https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_2&index=4">官方文档</a> <br>
 * 所撰写的订单查询实体类
 * 
 * @author leonliao
 *
 */
@JacksonXmlRootElement(localName = "xml")
@JsonInclude(Include.NON_EMPTY)
public class OrderQuery extends WeixinPaySignablePdu {
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
  private String mch_id;
  // 设备号
  @MaxLength(32)
  @SignField
  private String transaction_id;
  // 随机字符串
  @MaxLength(32)
  @SignField
  private String out_trade_no;
  // 业务结果
  @MaxLength(32)
  @Required
  @SignField
  private String nonce_str;

  public String getAppid() {
    return appid;
  }

  public OrderQuery setAppid(String appid) {
    this.appid = appid;
    return this;
  }

  public String getMch_id() {
    return mch_id;
  }

  public OrderQuery setMch_id(String mch_id) {
    this.mch_id = mch_id;
    return this;
  }

  public String getTransaction_id() {
    return transaction_id;
  }

  public OrderQuery setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
    return this;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public OrderQuery setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
    return this;
  }

  public String getNonce_str() {
    return nonce_str;
  }

  public OrderQuery setNonce_str(String nonce_str) {
    this.nonce_str = nonce_str;
    return this;
  }

  @Override
  public String toString() {
    return "OrderQuery [appid=" + appid + ", mch_id=" + mch_id + ", transaction_id="
        + transaction_id + ", out_trade_no=" + out_trade_no + ", nonce_str=" + nonce_str + ", "
        + super.toString() + "]";
  }
}
