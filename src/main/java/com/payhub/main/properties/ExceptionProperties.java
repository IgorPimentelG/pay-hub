package com.payhub.main.properties;

import java.time.LocalDateTime;

public record ExceptionProperties(String message, int code, LocalDateTime time) {}
