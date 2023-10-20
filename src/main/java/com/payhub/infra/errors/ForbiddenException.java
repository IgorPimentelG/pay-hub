package com.payhub.infra.errors;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException() {
		super("Access denied. You don't have permission to access this.");
	}
}
