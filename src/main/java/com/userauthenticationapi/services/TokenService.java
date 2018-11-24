package com.userauthenticationapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.*;
import java.util.Date;

@Component
public class TokenService {
  private Key signingKey;

  public TokenService() {
    this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String createToken(String subject, long expirationTime) {
    long now = System.currentTimeMillis();

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expirationTime))
        .signWith(this.signingKey)
        .compact();
  }
}
