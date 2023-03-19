package leoguedex.com.github.API_Sales_Java.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import leoguedex.com.github.API_Sales_Java.ApiSalesJavaApplication;
import leoguedex.com.github.API_Sales_Java.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.security.expiration}")
    private String jwtSecurityExpiration;

    @Value("${jwt.subscription.key}")
    private String jwtSubscriptionKey;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApiSalesJavaApplication.class);
        JwtService jwtService = run.getBean(JwtService.class);
        Users users = Users.builder().login("fulano").build();
        String token = jwtService.generateToken(users);
        System.out.println(token);
        boolean tokenValid = jwtService.tokenValid(token);
        System.out.println("Token is Valid? " + tokenValid);
        String loginUser = jwtService.getUserLogin(token);
        System.out.println(loginUser);
    }

    public String generateToken(Users users) {
        long minutesExpire = Long.valueOf(this.jwtSecurityExpiration);
        LocalDateTime plusMinutes = LocalDateTime.now().plusMinutes(minutesExpire);
        Instant toInstant = plusMinutes.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(toInstant);
        String token = Jwts.builder()
                .setSubject(users.getLogin())
                .setExpiration(from)
                .signWith(SignatureAlgorithm.HS512, jwtSubscriptionKey)
                .compact();
        return token;
    }

    public boolean tokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(jwtSubscriptionKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserLogin(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }

}