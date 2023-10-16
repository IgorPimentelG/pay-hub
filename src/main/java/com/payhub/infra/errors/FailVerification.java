package com.payhub.infra.errors;

public class FailVerification extends RuntimeException{
	public FailVerification() {
		super("Account verification failed.");
	}
}
