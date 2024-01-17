package com.example.usermanagement.authorization;

import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
public class TokenGenerator{

    private static final String SECRETKEY = "2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a";

    public String generateJWTToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 day

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRETKEY)
                .compact();
    }
}