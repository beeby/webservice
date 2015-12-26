package com.transfar.messageserver.chinaunicom.scheme;

import com.transfar.messageserver.scheme.MessageFactory;
import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.utils.MessageUtils;

public class SGIPMessageFactory implements MessageFactory{
	public SGIPMessage getMessage(int messageType) throws MessageServerException {
		switch (messageType) {
		case SGIPMessage.SGIP_BIND:
			return new SGIPBind();
		case SGIPMessage.SGIP_BIND_RESP:
			return new SGIPBindResp();
		case SGIPMessage.SGIP_DELIVER:
			return null;
		case SGIPMessage.SGIP_DELIVER_RESP:
			return null;
		case SGIPMessage.SGIP_SUBMIT:
			return new SGIPSubmit();
		case SGIPMessage.SGIP_SUBMIT_RESP:
			return new SGIPSubmitResp();
		case SGIPMessage.SGIP_UNBIND:
			return new SGIPUnbind();
		case SGIPMessage.SGIP_UNBIND_RESP:
			return new SGIPUnbindResp();
		default:
			throw new MessageServerException(MessageServerException.MESSAGE_FORMAT_ERROR);
		}
	}
	public SGIPMessage getMessage(byte [] msgBytes) throws MessageServerException {
		if (msgBytes.length < 20) {
			throw new MessageServerException(MessageServerException.MESSAGE_FORMAT_ERROR);
		}
		SGIPMessage msg=getMessage(MessageUtils.bytesToInt(msgBytes, 4));
		msg.setMessageBytes(msgBytes);
		return msg;
		
	}
}
