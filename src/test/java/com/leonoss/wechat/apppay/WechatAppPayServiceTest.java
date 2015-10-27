package com.leonoss.wechat.apppay;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.leonoss.wechat.apppay.cfg.AppPayConf;
import com.leonoss.wechat.apppay.cfg.HttpConf;
import com.leonoss.wechat.apppay.client.dto.WechatAppPayRequest;
import com.leonoss.wechat.apppay.dto.OrderQuery;
import com.leonoss.wechat.apppay.dto.OrderQueryResponse;
import com.leonoss.wechat.apppay.dto.UnifiedOrder;
import com.leonoss.wechat.apppay.dto.UnifiedOrderResponse;
import com.leonoss.wechat.apppay.util.Util;

public class WechatAppPayServiceTest {
  String key = "63d8ddd6ff234233edefb2633c218f7c";
  WechatAppPayServiceImpl service;
  String device_info = "013467007045764";
  String body = "Ipad mini 16G 白色";
  String detail = "Ipad mini 16G 白色 二手";
  String attach = "说明";
  String out_trade_no = "1217752501201407033233368018";
  int total_fee = 888;
  String spbill_create_ip = "8.8.8.8";
  String time_start = "20091225091010";
  String time_expire = "20091227091010";
  String goods_tag = "WXG";
  String notify_url = "http://yoursite.com";
  String product_id = "12235413214070356458058";
  String nounce_str = "nounce";
  String appid = "wx333386e333333c44";
  String mch_id = "1611111110";

  @Before
  public void setUp() throws Exception {
    AppPayConf appPayConf = new AppPayConf();
    appPayConf.appId = appid;
    appPayConf.key = key;
    appPayConf.mchId = mch_id;
    HttpConf httpConf = new HttpConf();
    httpConf.timeOutInSeconds = 30;
    httpConf.numberOfConnections = 20;

    service = new WechatAppPayServiceImpl();
    service.init(appPayConf, httpConf);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testSendingUnifiedOrder() throws Exception {
    UnifiedOrder order = new UnifiedOrder();
    order.setAppid(appid).setMch_id(mch_id).setAttach(attach).setBody(body).setDetail(detail)
        .setDevice_info(device_info).setGoods_tag(goods_tag).setLimit_pay("limit_pay")
        .setNonce_str(nounce_str);
    order.setNotify_url(notify_url);
    order.setOut_trade_no(out_trade_no);
    order.setProduct_id(product_id);
    order.setSpbill_create_ip(spbill_create_ip);
    order.setTime_startByDate(Util.fromWeixinDateFormat(time_start));
    order.setTime_expireByDate(Util.fromWeixinDateFormat(time_expire));
    order.setTotal_fee(total_fee);
    System.out.println(order.toString());

    try {
      UnifiedOrderResponse response = service.unifiedOrder(order);
      assertNotNull(response);
    } finally {
      service.close();
    }
  }

  @Test
  public void testAppRequestGeneration() {
    WechatAppPayRequest request = service.createPayRequestForClient("prepayId");
    assertNotNull(request.getSign());
    System.out.println(request);
  }

  @Test
  public void testOrderQuery() throws Exception {
    OrderQuery oq = new OrderQuery();
    oq.setAppid(appid).setMch_id(mch_id).setNonce_str(nounce_str).setOut_trade_no(out_trade_no)
        .setTransaction_id("transaction_id");
    System.out.println("Order query: " + oq);

    try {
      OrderQueryResponse response = service.queryOrder(oq);
      assertNotNull(response);
    } finally {
      service.close();
    }
  }
}
