package com.payhub.main.properties;

public class TokenProperties {

	private String secretKey;
	private long validityInMilliseconds;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getValidityInMilliseconds() {
		return validityInMilliseconds;
	}

	public void setValidityInMilliseconds(long validityInMilliseconds) {
		this.validityInMilliseconds = validityInMilliseconds;
	}
}
