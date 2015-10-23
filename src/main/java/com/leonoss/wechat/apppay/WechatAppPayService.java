package com.leonoss.wechat.apppay;

import java.io.Closeable;

import com.leonoss.wechat.apppay.cfg.AppPayConf;
import com.leonoss.wechat.apppay.cfg.HttpConf;
import com.leonoss.wechat.apppay.client.dto.WechatAppPayRequest;
import com.leonoss.wechat.apppay.dto.PaymentNotification;
import com.leonoss.wechat.apppay.dto.UnifiedOrder;
import com.leonoss.wechat.apppay.dto.UnifiedOrderResponse;
import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.exception.WechatAppPayServiceException;

/**
 * 微信App支付服务入口
 * @author leon
 *
 */
public interface WechatAppPayService extends Closeable{
	/**
	 * 提供配置以初始化服务
	 * @param conf 微信应用配置
	 * @param httpConf Http配置信息
	 */
	public void init(AppPayConf conf, HttpConf httpConf);
	/**
	 * 调用统一下单接口
	 * @param order
	 * @return
	 * @throws WechatAppPayServiceException
	 */
	public UnifiedOrderResponse unifiedOrder(UnifiedOrder order) throws WechatAppPayServiceException;
	/**
	 * 将收到的异步通知xml转成Java对象以供业务处理
	 * @param xml
	 * @return
	 * @throws MalformedPduException
	 */
	public PaymentNotification parsePaymentNotificationXml(String xml) throws MalformedPduException;
	/**
	 * 生成客户端支付请求，需要提供调用统一下单接口后收到的prepay_id
	 * @param prepayId 调用统一下单接口后收到的prepay_id
	 * @return
	 * @throws WechatAppPayServiceException
	 */
	public WechatAppPayRequest createPayRequestForClient(String prepayId);
}
