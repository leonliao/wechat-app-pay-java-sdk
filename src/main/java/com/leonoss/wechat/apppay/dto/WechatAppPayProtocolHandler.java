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
	private static Logger logger = LoggerFactory
			.getLogger(WechatAppPayProtocolHandler.class);

	/**
	 * 
	 * @param pdu
	 * @return Marshalled XML
	 */
	public static String marshalToXml(WeixinPayPdu pdu) {
		try {
			if (pdu instanceof WeixinPaySignablePdu) {
				WeixinPaySignablePdu signablePdu = (WeixinPaySignablePdu) pdu;
				if (signablePdu.getSecret() == null) {
					throw new IllegalArgumentException("Secret is not set.");
				}
				signablePdu.setSign(Util
						.validateFieldsAndGenerateWxSignature(signablePdu));
			}

			return xmlMapper.writeValueAsString(pdu);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Can not serialize " + pdu, e);
		}
	}

	public static <T extends WeixinPaySignablePdu> T unmarshalFromXml(
			String xml, Class<T> expectedType, String key)
			throws MalformedPduException {
		try {
			T object = xmlMapper.readValue(xml, expectedType);
			validateSign((WeixinPaySignablePdu) object, key);
			return object;
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not deserialize " + xml
					+ " to type " + expectedType.getName(), e);
		}
	}

	public static <T extends WeixinPaySignablePdu> T unmarshalFromXml(
			InputStream xmlStream, Class<T> expectedType, String key)
			throws MalformedPduException {
		try {
			T object = xmlMapper.readValue(xmlStream, expectedType);
			validateSign((WeixinPaySignablePdu) object, key);
			return object;
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Can not deserialize stream to type "
							+ expectedType.getName(), e);
		}
	}

	public static <T extends WeixinPayPdu> T unmarshalFromXml(String xml,
			Class<T> expectedType) throws MalformedPduException {
		try {
			T object = xmlMapper.readValue(xml, expectedType);
			return object;
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not deserialize " + xml
					+ " to type " + expectedType.getName(), e);
		}
	}

	public static <T extends WeixinPayPdu> T unmarshalFromXml(
			InputStream xmlStream, Class<T> expectedType)
			throws MalformedPduException {
		try {
			T object = xmlMapper.readValue(xmlStream, expectedType);
			return object;
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Can not deserialize stream to type "
							+ expectedType.getName(), e);
		}
	}

	private static void validateSign(WeixinPaySignablePdu pdu, String key)
			throws MalformedPduException {
		logger.debug("Validating pdu " + pdu);
		pdu.setSecret(key);
		String recomputedSign = Util.validateFieldsAndGenerateWxSignature(pdu);
		if (!pdu.getSign().equals(recomputedSign)) {
			throw new MalformedPduException("Received signature is  "
					+ pdu.getSign() + " but expected to be " + recomputedSign);
		}
	}
}
