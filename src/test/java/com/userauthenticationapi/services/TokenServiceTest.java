package com.userauthenticationapi.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.Test;

import java.lang.reflect.Field;
import java.security.Key;
import java.security.SignatureException;

import static org.junit.Assert.assertEquals;

public class TokenServiceTest {

  private TokenService tokenService = new TokenService();

  @Test
  public void createToken_returnsJWTTokenForSubject() throws Exception {
    String subject = "subject";
    long expirationTime = 1000L;
    Field keyField = tokenService.getClass().getDeclaredField("signingKey");
    keyField.setAccessible(true);
    Key key = (Key) keyField.get(tokenService);

    String token = tokenService.createToken(subject, expirationTime);

    assertEquals(Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject(), subject);
  }

  @Test
  public void validateToken_givenValidToken_doesNotThrowExceptions() {
    String token = tokenService.createToken("subject", 10000L);

    tokenService.validateToken(token);
  }

  @Test(expected = ExpiredJwtException.class)
  public void validateToken_givenExpiredToken_doesNotThrowExceptions() {
    String token = tokenService.createToken("subject", 0L);

    tokenService.validateToken(token);
  }

  @Test(expected = MalformedJwtException.class)
  public void validateToken_givenMalformedToken_doesNotThrowExceptions() {
    String token = "token";

    tokenService.validateToken(token);
  }

  @Test(expected = SignatureException.class)
  public void validateToken_givenTokenWithInvalidSignature_doesNotThrowExceptions() {
    String token = tokenService.createToken("subject", 10000L);
    tokenService = new TokenService();

    tokenService.validateToken(token);
  }
}
