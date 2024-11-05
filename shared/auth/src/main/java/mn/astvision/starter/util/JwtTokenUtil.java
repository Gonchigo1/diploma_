package mn.astvision.starter.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.*;

/**
 * @author digz6666
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private Long expiration;

    private final static long EXPIRATION_LONG = 365 * 24 * 3600; // d * h * sec

    private final UserRepository userRepository;

    private SecretKey secretKey;

    @PostConstruct
    public void initSecretKey() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        Claims claims = getClaimsFromToken(token);
        //log.debug("Got claims from token: " + claims);
        if (claims != null) {
            username = claims.getSubject();
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created = null;
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            created = new Date((Long) claims.get(Claims.ISSUED_AT));
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expirationDate = null;
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            expirationDate = claims.getExpiration();
        }
        return expirationDate;
    }

    public String refreshToken(String token) {
        String refreshedToken = null;
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims, false);
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token) {
        String username = getUsernameFromToken(token);
//        Date created = getCreatedDateFromToken(token);

        User user = userRepository.findByEmailAndDeletedFalse(username);
        if (user != null) {
            return Objects.equals(username, user.getEmail()) && !(isTokenExpired(token));
        } else {
            return !(isTokenExpired(token));
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        if (StringUtils.hasText(token)) {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        return claims;
    }

    public Date generateExpirationDate(boolean isLong) {
        if (isLong) {
            return new Date(System.currentTimeMillis() + EXPIRATION_LONG * 1000);
        } else {
            return new Date(System.currentTimeMillis() + expiration * 1000);
        }
    }

    private Boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate != null && expirationDate.before(new Date());
    }

    public String generateToken(String username, boolean isLong) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, username);
        claims.put(Claims.AUDIENCE, "WEB");
        claims.put(Claims.ISSUER, "Ast Starter");
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims, isLong);
    }

    private String generateToken(Map<String, Object> claims, boolean isLong) {
        return Jwts.builder()
                .claims(claims)
                .expiration(generateExpirationDate(isLong))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
}
