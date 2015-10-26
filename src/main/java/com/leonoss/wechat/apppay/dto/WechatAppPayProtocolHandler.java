package com.leonoss.wechat.apppay.dto;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.leonoss.wechat.apppay.exception.MalformedPduException;
import com.leonoss.wechat.apppay.util.Util;

public abstract class WechatAppPayProtocolHandler {
  private static ObjectMapper xmlMapper = new XmlMapper();
  private static Logger logger = LoggerFactory.getLogger(WechatAppPayProtocolHandler.class);

  public static <T extends WeixinPaySignablePdu> T unmarshalFromXmlAndValidateSignature(String xml,
      Class<T> expectedType, String key) throws MalformedPduException {
    T object = unmarshalFromXml(xml, expectedType);
    if (!Util.validateSign((WeixinPaySignablePdu) object, key)) {
      throw new MalformedPduException("Received PDU's signature is invalid.");
    }
    return object;
  }

  public static <T extends WeixinPaySignablePdu> T unmarshalFromXmlAndValidateSignature(
      InputStream xmlStream, Class<T> expectedType, String key) throws MalformedPduException {
    T object = unmarshalFromXml(xmlStream, expectedType);
    if (!Util.validateSign((WeixinPaySignablePdu) object, key)) {
      throw new MalformedPduException("Received PDU's signature is invalid.");
    }
    return object;
  }

  public static <T extends WeixinPayPdu> T unmarshalFromXml(String xml, Class<T> expectedType)
      throws MalformedPduException {
    try {
      T object = xmlMapper.readValue(xml, expectedType);
      logger.debug("Unmarshalled {} to {}", xml, expectedType);
      return object;
    } catch (IOException e) {
      throw new IllegalArgumentException("Can not deserialize " + xml + " to type "
          + expectedType.getName(), e);
    }
  }

  public static <T extends WeixinPayPdu> T unmarshalFromXml(InputStream xmlStream,
      Class<T> expectedType) throws MalformedPduException {
    try {
      T object = xmlMapper.readValue(xmlStream, expectedType);
      return object;
    } catch (IOException e) {
      throw new IllegalArgumentException("Can not deserialize stream to type "
          + expectedType.getName(), e);
    }
  }

  /**
   * 封装成XML
   * @param pdu
   * @param key，如果非空，则尝试进行签名
   * @return
   */
  public static String marshalToXml(WeixinPayPdu pdu, String key) {
    try {
      if (!Util.isEmpty(key)) {
        if (pdu instanceof WeixinPaySignablePdu) {
          WeixinPaySignablePdu signablePdu = (WeixinPaySignablePdu) pdu;
          signablePdu.setSign(Util.validateFieldsAndGenerateWxSignature(signablePdu, key));
        }
      }
      return xmlMapper.writeValueAsString(pdu);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Can not serialize " + pdu, e);
    }
  }

}
