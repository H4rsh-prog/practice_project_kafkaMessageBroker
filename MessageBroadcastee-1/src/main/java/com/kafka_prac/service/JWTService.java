package com.kafka_prac.service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
	@Value("${spring.kafka.consumer.group-id}")
	private String groupID;
	private int expirationDuration = 600000;
	private String secretKey = "LONG_ENOUGH_SECRET_KEY_TO_BE_AT_LEAST_256_BITS";
	
	public String getToken() {
		return Jwts.builder()
				.subject(this.groupID)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + this.expirationDuration))
				.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(this.secretKey.getBytes()))
				.compact();
	}
	public boolean validate(String token) {
		if(this.groupID.equals(this.extractClaim(token, Claims::getSubject)) && this.extractClaim(token, Claims::getExpiration).after(new Date(System.currentTimeMillis()))) {
			return true;
		}
		System.out.println("SUBJECT MATCHED : "+this.groupID.equals(this.extractClaim(token, Claims::getSubject))+" VALUES = ["+this.groupID+","+this.extractClaim(token, Claims::getSubject)+"]");
		System.out.println("EXPIRED : "+!this.extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()))+" VALUES = ["+this.extractClaim(token, Claims::getExpiration)+","+new Date(System.currentTimeMillis())+"]");
		return false;
	}
	private <T> T extractClaim(String token , Function<Claims, T> resolver) {
		return resolver.apply(this.extractAllClaims(token));
	}
	private Claims extractAllClaims(String token) {
		return (Claims) Jwts.parser().setSigningKey(Base64.getEncoder().encode(this.secretKey.getBytes())).build().parse(token).getPayload();
	}
}
