package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptorService {

	@Value("${security.aes.secret-key}")
	private final String secretKey = "";

	private SecretKeySpec secretKeySpec;

	public String encrypt(String data) {
		try {
			configureKey();
			var cipher = cipherFactory();
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			return Base64.getEncoder().encodeToString(
				cipher.doFinal(data.getBytes(StandardCharsets.UTF_8))
			);
		} catch (Exception e) {
			throw new BadRequestException("Failed to encrypt data.");
		}
	}

	public String decrypt(String data) {
		try {
			configureKey();
			var cipher = cipherFactory();
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			return new String(
				cipher.doFinal(Base64.getDecoder().decode(data))
			);
		} catch (Exception e) {
			throw new BadRequestException("Failed to decrypt data.");
		}
  }

	private void configureKey() {
		try {
			MessageDigest sha =  MessageDigest.getInstance("SHA-1");
			byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKeySpec = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			throw new BadRequestException("Failed to create secret key.");
		}
	}

	private Cipher cipherFactory() throws NoSuchPaddingException, NoSuchAlgorithmException {
		return Cipher.getInstance("AES/ECB/PKCS5PADDING");
	}
}
