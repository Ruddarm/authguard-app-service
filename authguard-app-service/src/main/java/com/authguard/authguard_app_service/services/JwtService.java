package com.authguard.authguard_app_service.services;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class JwtService {
    @Value("${jwt.privateKey}")
    private String privateKey;
    @Value("${jwt.publicKey}")
    private String publicKey;

    private RSAPrivateKey getPrivateKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private RSAPublicKey getPublicKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(decoded));
    }

    /*
     * Create a acesstoken for inter microservice communication
     * 
     * @param Hashmap<String,Object> a map of all calims
     * 
     * @param String subject
     * 
     * @return access token sing with private key
     */
    public String serviceAccessToken(String subject, Map<String, Object> claimsMap)
            throws InvalidKeyException, Exception {
        long now = System.currentTimeMillis();
        return Jwts.builder().issuer("auth-guard-service").subject(subject).claims(claimsMap).issuedAt(new Date(now))
                .expiration(new Date(now + 5 * 60 * 1000))
                .signWith(getPrivateKey(), Jwts.SIG.RS256).compact();
    }

    /*
     * Verify service access token inter microservice communication
     * 
     * @param String token singed with private key
     * 
     * @return Claims all claims.
     */
    public Claims verifyServiceToken(String token) throws JwtException, IllegalArgumentException, Exception {
        Claims claims = Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();
        return claims;
    }

    /*
     * Get service id from token if valid
     * 
     * @param String token sign wiht private key
     * 
     * @return String of service id
     */

    public String getServiceId(String token) throws JwtException, IllegalArgumentException, Exception {
        Claims claims = verifyServiceToken(token);
        return claims.getSubject();
    }
}
