package com.transfar.messageserver.chinaunicom.scheme;


import com.transfar.messageserver.vo.InteractionBasicObject;

public abstract class UMSRequest extends InteractionBasicObject {

	protected String spId;
	protected String spUsername;
	protected String spPassword;

	
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpUsername() {
		return spUsername;
	}

	public void setSpUsername(String spUsername) {
		this.spUsername = spUsername;
	}

	public String getSpPassword() {
		return spPassword;
	}

	public void setSpPassword(String spPassword) {
		this.spPassword = spPassword;
	}
}
