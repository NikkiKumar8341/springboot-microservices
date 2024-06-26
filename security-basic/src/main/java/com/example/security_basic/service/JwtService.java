package com.example.security_basic.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {



    private static final String SECRET="!@#$%^FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";

    public String generateToken(String userName, List<String> roles){
        Map<String, Object> claims = new HashMap<>();
//        claims.put("roles",roles);

        // Convert the roles list to key-value pairs
        for (String role : roles) {
            claims.put(role, true); // Assuming true indicates the user has the role
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);

        return claimResolver.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }


    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final  String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
