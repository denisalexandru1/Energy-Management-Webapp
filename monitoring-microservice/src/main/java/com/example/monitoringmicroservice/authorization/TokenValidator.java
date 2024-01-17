package com.example.monitoringmicroservice.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Base64;

public class TokenValidator {

    private static final String SECRETKEY = "2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a";
    private static Base64.Decoder decoder = Base64.getUrlDecoder();
    public boolean validate(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRETKEY)
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new java.util.Date());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
