package com.transfar.messageserver.vo;

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMElement;

import com.transfar.messageserver.utils.MessageServerException;


public class CommonRequest extends InteractionBasicObject {

	private String rootName="CommonRequest";
	
	private HashMap<String,Object> map=new HashMap<String,Object>();
	
	public CommonRequest(String rootName) throws MessageServerException {
		if ( rootName==null) {
			throw new MessageServerException(MessageServerException.PARAM_NULL);
		}
		this.rootName=rootName;
	}
	
	@Override
	public String getRootName() {

		return rootName;
	}

	public void setAttribute(String name, String value)  {

		map.put(name, value);
	}
	
	public Object getAttribute(String name) {
		return map.get(name);
	}
	
	@Override
	protected Map<String, Object> getAttributesMap() {
		return map;
	}

	@Override
	protected void setAttribute(String name, OMElement value)
			throws MessageServerException {
		// TODO Auto-generated method stub
		
	}

}
