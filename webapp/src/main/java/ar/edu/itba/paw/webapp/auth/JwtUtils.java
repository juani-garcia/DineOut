package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    private static final long ACCESS_EXPIRATION_TIME = 30L * 60 * 1000,
            REFRESH_EXPIRATION_TIME = 7L * 24 * 60 * 60 * 1000;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String REFRESH_CLAIM = "refresh-claim";

    private final Key secretKey;
    private final JwtParser parser;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);


    @Autowired
    public JwtUtils(Environment env) {
        byte[] secret_bytes = Base64.getDecoder().decode(env.getProperty("jwt.key"));
        this.secretKey = Keys.hmacShaKeyFor(secret_bytes);
        this.parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public String getToken(User user) {
        return Jwts.builder()
                .setClaims(getClaims(user))
                .signWith(secretKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    public String getRefreshToken(User user) {
        return Jwts.builder()
                .setClaims(getRefreshClaims(user))
                .signWith(secretKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        String username = claims.getSubject();

        LOGGER.debug("Received token with {} as subject", username);
        return username;
    }

    private Claims getClaims(User user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        claims.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME));
        claims.put("userId", user.getId());
        if (user.getRestaurant() != null) {
            claims.put("restaurantId", user.getRestaurant().getId());
        }

        StringBuilder sb = new StringBuilder();

        for(UserRole ur : user.getRoles()) {
            sb.append(ur.getRoleName());
            sb.append(';');
        }

        claims.put("roles", sb.toString());
        return claims;
    }

    private Claims getRefreshClaims(User user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        claims.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME));
        claims.put(REFRESH_CLAIM, true);

        return claims;
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        Boolean value = claims.get(REFRESH_CLAIM, Boolean.class);

        return value != null && value;
    }

    public boolean isValidToken(String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
