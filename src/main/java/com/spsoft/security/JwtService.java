package com.spsoft.security;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
	private static final long TOKEN_EXPIRATION = TimeUnit.MINUTES.toMillis(200);
	private static final String ISSUER = "DCB";

	// Generate JWT token with user details (claims)
	public String generateTokenWithClaims(Map<String, Object> claims, String emailId) {
		JwtBuilder builder = Jwts.builder().setClaims(claims) // Set user details in claims
				.setSubject(emailId) // Set email as subject
				.setIssuer(ISSUER).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION)).signWith(generateKey()); // Signing
																													// with
																													// HMAC
																													// key

		return builder.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String userName = extractUserName(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public static String extractUserName(String token) {
		return extractAllClaims(token).getSubject();
	}

	public String extractUserId(String token) {
		return (String) extractAllClaims(token).get("userId");
	}

	public String extractEmailId(String token) {
		return (String) extractAllClaims(token).get("emailId");
	}

	public String extractPhoneNumber(String token) {
		return (String) extractAllClaims(token).get("phoneNumber");
	}

	public String extractRoleName(String token) {
		return (String) extractAllClaims(token).get("roleName");
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	private static Claims extractAllClaims(String token) {
		byte[] decodedKey = Decoders.BASE64.decode(SECRET_KEY);
		JwtParser parser = Jwts.parserBuilder().setSigningKey(decodedKey).build();
		return parser.parseClaimsJws(token).getBody();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	private PrivateKey generatePrivateKey() {
		return Keys.keyPairFor(SignatureAlgorithm.RS256).getPrivate();
	}
}
