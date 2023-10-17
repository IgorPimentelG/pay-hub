package com.payhub.infra.errors;

public class ExpiredVerification extends RuntimeException{
	public ExpiredVerification() {
		super("Account verification was expired. A new code has been sent to your email.");
	}
}
