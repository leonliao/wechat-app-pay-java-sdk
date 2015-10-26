package com.leonoss.wechat.apppay;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leonoss.wechat.apppay.cfg.AppPayConf;
import com.leonoss.wechat.apppay.cfg.HttpConf;
import com.leonoss.wechat.apppay.client.dto.WechatAppPayRequest;
import com.leonoss.wechat.apppay.dto.OrderQuery;
import com.leonoss.wechat.apppay.dto.OrderQueryResponse;
import com.leonoss.wechat.apppay.dto.PaymentNotification;
import com.leonoss.wechat.apppay.dto.UnifiedOrder;
import com.leonoss.wechat.apppay.dto.UnifiedOrderResponse;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.exception.WechatAppPayServiceException;
import com.leonoss.wechat.apppay.util.Util;

public class WechatAppPayServiceImpl extends WechatHttpCapableClient implements WechatAppPayService {
  private AppPayConf config;
  private HttpConf httpConf;
  private static Logger logger = LoggerFactory.getLogger(WechatAppPayServiceImpl.class);
  private static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
  private static String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

  @Override
  public void init(AppPayConf conf, HttpConf httpConf) {
    this.config = conf;
    this.httpConf = httpConf;
    validateConf();
    super.initHttp(this.httpConf);
  }

  private void validateConf() {
    if (config == null || !config.isReady()) {
      throw new IllegalStateException("Config must be not null or missing settings in config:"
          + config);
    }
  }

  @Override
  public UnifiedOrderResponse unifiedOrder(UnifiedOrder order) throws WechatAppPayServiceException {
    validateConf();
    String responseText = null;
    try {
      // 序列化成xml
      String xml = WechatAppPayProtocolHandler.marshalToXml(order, config.key);
      logger.debug("Sending to wechat for unified order: " + xml);
      // 发到服务器并收回XML响应
      responseText = sendHttpPostAndReturnString(UNIFIED_ORDER_URL, xml);
      if (responseText == null) {
        throw new WechatAppPayServiceException(
            "Unified order API to Weixin failed! Received empty response. Order Info: "
                + order.toString());
      }
      // 反序列化为Java对象并返回
      UnifiedOrderResponse unifiedOrderResponse = WechatAppPayProtocolHandler.unmarshalFromXml(
          responseText, UnifiedOrderResponse.class);
      logger.debug("Response from WX is {}", unifiedOrderResponse);
      return unifiedOrderResponse;

    } catch (IOException e) {
      logger.warn("Failed to call Weixin for order {} ", order, e);
      throw new WechatAppPayServiceException("Unified order API to Weixin failed! Order Info: "
          + order.toString(), e);
    } catch (MalformedPduException e) {
      logger.warn("Failed to unmarshall the order response {} ", responseText, e);
      throw new WechatAppPayServiceException("Failed to unmarshall the order response: "
          + responseText + " for the order: " + order.toString(), e);
    }
  }

  @Override
  public PaymentNotification parsePaymentNotificationXml(String xml) throws MalformedPduException {
    validateConf();
    return WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(xml,
        PaymentNotification.class, config.key);
  }

  @Override
  public WechatAppPayRequest createPayRequestForClient(String prepayId) {
    WechatAppPayRequest request = new WechatAppPayRequest();
    request.setAppid(config.appId);
    request.setAppPackage("Sign=WXPay");
    request.setPartnerid(config.mchId);
    request.setPrepayid(prepayId);
    request.setTimestamp(Long.toString(System.currentTimeMillis() / 1000));
    request.setNoncestr(Util.generateString(32));
    return request;
  }

  @Override
  public OrderQueryResponse queryOrder(OrderQuery orderQuery) throws WechatAppPayServiceException {
    validateConf();
    String responseText = null;
    try {
      // 序列化成xml
      String xml = WechatAppPayProtocolHandler.marshalToXml(orderQuery, config.key);
      logger.debug("Sending to wechat for order query: " + xml);
      // 发到服务器并收回XML响应
      responseText = sendHttpPostAndReturnString(ORDER_QUERY_URL, xml);
      if (responseText == null) {
        throw new WechatAppPayServiceException(
            "Order Query API to Weixin failed! Received empty response. OrderQuery Info: "
                + orderQuery.toString());
      }
      // 反序列化为Java对象并返回
      OrderQueryResponse orderQueryResponse = WechatAppPayProtocolHandler.unmarshalFromXml(
          responseText, OrderQueryResponse.class);
      logger.debug("Response from WX for order query is {}", orderQueryResponse);
      return orderQueryResponse;
    } catch (IOException e) {
      logger.warn("Failed to call Weixin for order {} ", orderQuery, e);
      throw new WechatAppPayServiceException("Order Query API to Weixin failed! Query Info: "
          + orderQuery.toString(), e);
    } catch (MalformedPduException e) {
      logger.warn("Failed to unmarshall the order query response {} ", responseText, e);
      throw new WechatAppPayServiceException("Failed to unmarshall the order query response: "
          + responseText + " for querying the order: " + orderQuery.toString(), e);
    }
  }
}
