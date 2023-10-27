package com.payhub.infra.errors;

public class FailTransaction extends RuntimeException{
	public FailTransaction() {
		super("Transaction failed.");
	}
}
