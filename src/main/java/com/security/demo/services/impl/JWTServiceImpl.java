package com.security.demo.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.demo.services.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JWTServiceImpl implements JWTService {
    public String generateToken(UserDetails userDetails){
        
        return Jwts.builder().subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() *1000*60*24))
        .signWith(getSignature(),Jwts.SIG.HS512).compact();
    }
    public String generateRefreshToken(Map<String,Object> extraclaims,UserDetails userDetails){
        return Jwts.builder().claims(extraclaims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() *604800))
        .signWith(getSignature(),Jwts.SIG.HS512).compact();
    }
    public String extractUsername(String token)
    {
        final Jws<Claims> parsed = extractAllClaim(token);
        String username =  parsed.getPayload().getSubject();
        return username;
    }
    private <T> T extractClaim(String token,Function<Claims, T> claimsResolver){
        final Claims claim = extractAllClaim(token).getPayload();
        return claimsResolver.apply(claim);
    }
    private SecretKey getSignature(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("thisismyscreatfejfjsfjsefsjfdhfdhjdsjfsjdfhfghfghgfhfhfhffhfhfhfhfsjghghfgfgeiyfeufuyretfhujshdfhsgfusdcode"));
    }
    private Jws<Claims> extractAllClaim(String token){
        return Jwts.parser().verifyWith(getSignature()).build().parseSignedClaims(token);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
       
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
