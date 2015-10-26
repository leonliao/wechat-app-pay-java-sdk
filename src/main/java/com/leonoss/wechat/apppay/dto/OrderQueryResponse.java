package com.leonoss.wechat.apppay.dto;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.leonoss.wechat.apppay.util.Util;

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
public class OrderQueryResponse extends WeixinPaySignablePdu {
  /**
   * 
   */
  private static final long serialVersionUID = 8238944728595459655L;

  // 返回状态码
  @MaxLength(16)
  @Required
  @SignField
  private String return_code;
  // 返回信息
  @MaxLength(128)
  @SignField
  private String return_msg;
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
  // 随机字符串
  @MaxLength(32)
  @Required
  @SignField
  private String nonce_str;
  // 业务结果
  @MaxLength(16)
  @Required
  @SignField
  private String result_code;
  // 错误代码
  @MaxLength(32)
  @SignField
  private String err_code;
  // 错误代码描述
  @MaxLength(128)
  @SignField
  private String err_code_des;
  // 设备号
  @MaxLength(32)
  @SignField
  private String device_info;
  // 用户标识
  @MaxLength(128)
  @Required
  @SignField
  private String openid;
  // 是否关注公众账号
  @MaxLength(1)
  @SignField
  private String is_subscribe;
  // 交易类型
  @MaxLength(16)
  @Required
  @SignField
  private String trade_type;
  // 交易状态
  @MaxLength(32)
  @Required
  @SignField
  private String trade_state;
  // 付款银行
  @MaxLength(16)
  @Required
  @SignField
  private String bank_type;
  // 总金额
  @Required
  @SignField
  private Integer total_fee;
  // 货币种类
  @MaxLength(8)
  @SignField
  private String fee_type;
  // 现金支付金额
  @Required
  @SignField
  private Integer cash_fee;
  // 现金支付货币类型
  @MaxLength(16)
  @SignField
  private String cash_fee_type;
  // 代金券或立减优惠金额
  @SignField
  private Integer coupon_fee;
  // 代金券或立减优惠使用数量
  @SignField
  private Integer coupon_count;
  // 代金券或立减优惠批次ID
  @MaxLength(20)
  @SignField
  private String coupon_batch_id_$n;
  // 代金券或立减优惠ID
  // 微信支付订单号
  @MaxLength(32)
  @Required
  @SignField
  private String transaction_id;
  // 商户订单号
  @MaxLength(32)
  @Required
  @SignField
  private String out_trade_no;
  // 附加数据
  @MaxLength(128)
  @SignField
  private String attach;
  // 支付完成时间
  @MaxLength(14)
  @Required
  @SignField
  private String time_end;
  // 交易状态描述
  @MaxLength(256)
  @Required
  @SignField
  private String trade_state_desc;

  /**
   * 用于存储动态属性，例如coupon_fee_1，coupon_fee_2等等
   */
  @SignField
  @JsonIgnore
  private Map<String, Object> dynamicFields = new HashMap<String, Object>();

  @JsonAnySetter
  public OrderQueryResponse set(String name, Object value) {
    /*
     * //代金券或立减优惠ID
     * 
     * @MaxLength(20) String coupon_id_$n; //单个代金券或立减优惠支付金额 Integer coupon_fee_$n;
     */
    if (name.startsWith("coupon_id")) {
      if (value instanceof String) {
        String strValue = (String) value;
        if (strValue != null && strValue.length() > 20) {
          throw new IllegalArgumentException("coupon id' length must be less or equal than 20");
        }
      } else {
        throw new IllegalArgumentException("coupon id must be String");
      }
    }
    dynamicFields.put(name, value);
    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getDynamicFields() {
    return Collections.unmodifiableMap(dynamicFields);
  }

  public String getDevice_info() {
    return device_info;
  }

  public OrderQueryResponse setDevice_info(String device_info) {
    this.device_info = device_info;
    return this;
  }

  public String getOpenid() {
    return openid;
  }

  public OrderQueryResponse setOpenid(String openid) {
    this.openid = openid;
    return this;
  }

  public String getIs_subscribe() {
    return is_subscribe;
  }

  public OrderQueryResponse setIs_subscribe(String is_subscribe) {
    this.is_subscribe = is_subscribe;
    return this;
  }

  public String getTrade_type() {
    return trade_type;
  }

  public OrderQueryResponse setTrade_type(String trade_type) {
    this.trade_type = trade_type;
    return this;
  }

  public String getTrade_state() {
    return trade_state;
  }

  public OrderQueryResponse setTrade_state(String trade_state) {
    this.trade_state = trade_state;
    return this;
  }

  public String getBank_type() {
    return bank_type;
  }

  public OrderQueryResponse setBank_type(String bank_type) {
    this.bank_type = bank_type;
    return this;
  }

  public Integer getTotal_fee() {
    return total_fee;
  }

  public OrderQueryResponse setTotal_fee(Integer total_fee) {
    this.total_fee = total_fee;
    return this;
  }

  public String getFee_type() {
    return fee_type;
  }

  public OrderQueryResponse setFee_type(String fee_type) {
    this.fee_type = fee_type;
    return this;
  }

  public Integer getCash_fee() {
    return cash_fee;
  }

  public OrderQueryResponse setCash_fee(Integer cash_fee) {
    this.cash_fee = cash_fee;
    return this;
  }

  public String getCash_fee_type() {
    return cash_fee_type;
  }

  public OrderQueryResponse setCash_fee_type(String cash_fee_type) {
    this.cash_fee_type = cash_fee_type;
    return this;
  }

  public Integer getCoupon_fee() {
    return coupon_fee;
  }

  public OrderQueryResponse setCoupon_fee(Integer coupon_fee) {
    this.coupon_fee = coupon_fee;
    return this;
  }

  public Integer getCoupon_count() {
    return coupon_count;
  }

  public OrderQueryResponse setCoupon_count(Integer coupon_count) {
    this.coupon_count = coupon_count;
    return this;
  }

  public String getCoupon_batch_id_$n() {
    return coupon_batch_id_$n;
  }

  public OrderQueryResponse setCoupon_batch_id_$n(String coupon_batch_id_$n) {
    this.coupon_batch_id_$n = coupon_batch_id_$n;
    return this;
  }

  public String getTransaction_id() {
    return transaction_id;
  }

  public OrderQueryResponse setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
    return this;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public OrderQueryResponse setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
    return this;
  }

  public String getAttach() {
    return attach;
  }

  public OrderQueryResponse setAttach(String attach) {
    this.attach = attach;
    return this;
  }

  public String getTime_end() {
    return time_end;
  }

  public OrderQueryResponse setTime_end(String time_end) {
    this.time_end = time_end;
    return this;
  }

  public String getTrade_state_desc() {
    return trade_state_desc;
  }

  public OrderQueryResponse setTrade_state_desc(String trade_state_desc) {
    this.trade_state_desc = trade_state_desc;
    return this;
  }

  public String getReturn_code() {
    return return_code;
  }

  public OrderQueryResponse setReturn_code(String return_code) {
    this.return_code = return_code;
    return this;
  }

  public String getReturn_msg() {
    return return_msg;
  }

  public OrderQueryResponse setReturn_msg(String return_msg) {
    this.return_msg = return_msg;
    return this;
  }

  public String getAppid() {
    return appid;
  }

  public OrderQueryResponse setAppid(String appid) {
    this.appid = appid;
    return this;
  }

  public String getMch_id() {
    return mch_id;
  }

  public OrderQueryResponse setMch_id(String mch_id) {
    this.mch_id = mch_id;
    return this;
  }

  public String getNonce_str() {
    return nonce_str;
  }

  public OrderQueryResponse setNonce_str(String nonce_str) {
    this.nonce_str = nonce_str;
    return this;
  }

  public String getResult_code() {
    return result_code;
  }

  public OrderQueryResponse setResult_code(String result_code) {
    this.result_code = result_code;
    return this;
  }

  public String getErr_code() {
    return err_code;
  }

  public OrderQueryResponse setErr_code(String err_code) {
    this.err_code = err_code;
    return this;
  }

  public String getErr_code_des() {
    return err_code_des;
  }

  public OrderQueryResponse setErr_code_des(String err_code_des) {
    this.err_code_des = err_code_des;
    return this;
  }

  @JsonIgnore
  public Date getTime_endAsDate() {
    return Util.fromWeixinDateFormat(time_end);
  }

  public OrderQueryResponse setTime_endByDate(Date time_end) {
    this.time_end = Util.toWeixinDateFormat(time_end);
    return this;
  }

  @Override
  public String toString() {
    return "OrderQueryResponse [return_code=" + return_code + ", return_msg=" + return_msg
        + ", appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", result_code="
        + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des
        + ", device_info=" + device_info + ", openid=" + openid + ", is_subscribe=" + is_subscribe
        + ", trade_type=" + trade_type + ", trade_state=" + trade_state + ", bank_type="
        + bank_type + ", total_fee=" + total_fee + ", fee_type=" + fee_type + ", cash_fee="
        + cash_fee + ", cash_fee_type=" + cash_fee_type + ", coupon_fee=" + coupon_fee
        + ", coupon_count=" + coupon_count + ", coupon_batch_id_$n=" + coupon_batch_id_$n
        + ", transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no + ", attach="
        + attach + ", time_end=" + time_end + ", trade_state_desc=" + trade_state_desc
        + ", dynamicFields=" + dynamicFields + ", " + super.toString() + "]";
  }

}
