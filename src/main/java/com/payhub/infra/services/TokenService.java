package com.payhub.infra.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.credentials.AuthDto;
import com.payhub.infra.errors.UnauthorizedException;
import com.payhub.infra.repositories.ClientRepository;
import com.payhub.main.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Service
public class TokenService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private TokenProperties tokenProperties;

	public AuthDto createToken(Client client) {
		var now = new Date();
		var accessTokenExpiration = getAccessTokenExpiration(now);
		var refreshTokenExpiration = getRefreshTokenExpiration(now);

		var accessToken = getAccessToken(client, accessTokenExpiration);
		var refreshToken = getRefreshToken(client, now, refreshTokenExpiration);

		return new AuthDto(
		  accessToken,
		  refreshToken,
		  accessTokenExpiration,
		  refreshTokenExpiration
		);
	}

	public AuthDto refreshToken(String refreshToken) {
		if (refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
		} else {
			throw new UnauthorizedException();
		}

		JWTVerifier verifier = JWT.require(getAlgorithm()).build();
		DecodedJWT decodedJWT = verifier.verify(refreshToken);
		String subject = decodedJWT.getSubject();

		var client = clientService.findByEmail(subject);

		return createToken(client);
	}

	public String validateToken(String token) {
		return JWT.require(getAlgorithm())
		  .withIssuer("")
		  .build()
		  .verify(token)
		  .getSubject();
	}

	public Date getAccessTokenExpiration(Date now) {
		return new Date(now.getTime() + tokenProperties.getValidityInMilliseconds());
	}

	public Date getRefreshTokenExpiration(Date now) {
		return new Date(now.getTime() + (tokenProperties.getValidityInMilliseconds() * 3));
	}

	private String getAccessToken(Client client, Date expiration) {
		return JWT.create()
		  .withIssuer(getIssuer())
		  .withSubject(client.getEmail())
		  .withExpiresAt(expiration)
		  .sign(getAlgorithm());
	}

	private String getRefreshToken(Client client, Date now, Date expiration) {
		return  JWT.create()
		  .withIssuedAt(now)
		  .withExpiresAt(expiration)
		  .withSubject(client.getEmail())
		  .sign(getAlgorithm())
		  .strip();
	}

	private Algorithm getAlgorithm() {
		return Algorithm.HMAC256(tokenProperties.getSecretKey());
	}

	private String getIssuer() {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
		  .build()
		  .toUriString();
	}
}
