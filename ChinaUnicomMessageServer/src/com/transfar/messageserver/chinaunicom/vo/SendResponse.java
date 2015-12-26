package com.transfar.messageserver.chinaunicom.vo;

import java.util.HashMap;
import java.util.Map;

import com.transfar.messageserver.utils.MessageServerException;
import com.transfar.messageserver.vo.InteractionBasicObject;

public class SendResponse extends InteractionBasicObject {
	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String getRootName() {
		return "sendResponse";
	}

	@Override
	protected void setAttribute(String name, String value)
			throws MessageServerException {
		if ( name.equals("out")) {
			setResponse(value);
			return ;
		}
	}

	@Override
	protected Map<String, String> getAttributesMap() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("out", getResponse());
		return map;
	}

}
