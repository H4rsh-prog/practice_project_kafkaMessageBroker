package com.kafka_prac.service;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {
	@Value("${spring.kafka.consumer.group-id}")
	private String groupID;
	private int expirationDuration = 60000;
	
	public String getToken() {
		return Jwts.builder()
				.subject(this.groupID)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + this.expirationDuration))
				.compact();
	}
	public boolean validate(String token) {
		if(this.groupID.equals(this.extractClaim(token, Claims::getSubject)) && this.extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()))) {
			return true;
		}
		return false;
	}
	private <T> T extractClaim(String token , Function<Claims, T> resolver) {
		return resolver.apply(this.extractAllClaims(token));
	}
	private Claims extractAllClaims(String token) {
		return (Claims) Jwts.parser().build().parse(token).getPayload();
	}
}
