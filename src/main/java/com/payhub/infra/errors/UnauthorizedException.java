package com.payhub.infra.errors;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException() {
		super("You don't have permission to access this.");
	}
}
