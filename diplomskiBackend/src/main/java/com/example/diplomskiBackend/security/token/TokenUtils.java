package com.example.diplomskiBackend.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    @Value("${app.name}")
    private String APPLICATION_NAME;

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Value("${token.expiration}")
    private Long TOKEN_DURATION;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + TOKEN_DURATION * 1000);
        return Jwts.builder()
                .setIssuer(APPLICATION_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .claim("role", userDetails.getAuthorities().toString().substring(1, userDetails.getAuthorities().toString().length() - 1))
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET).compact();
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = this.getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }
}
