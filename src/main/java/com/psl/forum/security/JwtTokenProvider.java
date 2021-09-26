package com.psl.forum.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration.time}")
	private String jwtExpirationInMillis;
	
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return JWT.create()
				.withSubject(principal.getUsername())
				.withIssuedAt(Date.from(Instant.now()))
				.withExpiresAt(Date.from(Instant.now().plusMillis(Long.parseLong(jwtExpirationInMillis))))
				.sign(HMAC512(secret.getBytes()));
	}
	
	public String generateTokenWithUsername(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuedAt(Date.from(Instant.now()))
				.withExpiresAt(Date.from(Instant.now().plusMillis(Long.parseLong(jwtExpirationInMillis))))
				.sign(HMAC512(secret.getBytes()));
	}
	
	public boolean validateToken(String jwt) {
		JWTVerifier jwtVerifier = getJWTVerifier();
		jwtVerifier.verify(jwt);
		return true;
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier jwtVerifier;
		try {
			Algorithm algorithm = HMAC512(secret);
			jwtVerifier = JWT.require(algorithm).build();
		}catch (JWTVerificationException e) {
			throw new JWTVerificationException("Token cannot be verified");
		}
		return jwtVerifier;
	}
	
	public String getUsernameFromJwt(String token) {
		JWTVerifier jwtVerifier = getJWTVerifier();
		return jwtVerifier.verify(token).getSubject();
	}
	
	public Long getJwtExpirationInMillis() {
		return Long.parseLong(jwtExpirationInMillis);
	}

}
