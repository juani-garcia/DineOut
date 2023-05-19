package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    private static final long ACCESS_EXPIRATION_TIME = 15L * 60 * 1000,
            REFRESH_EXPIRATION_TIME = 7L * 24 * 60 * 60 * 1000;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private final String SECRET_KEY;

    @Autowired
    public JWTUtils(Environment env) {
        this.SECRET_KEY = env.getProperty("jwt.key");
    }

    public String getToken(User user) {
        return Jwts.builder()
                .setClaims(getClaims(user))
                .signWith(SIGNATURE_ALGORITHM, this.SECRET_KEY)
                .compact();
    }

    private Claims getClaims(User user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        claims.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME));
        claims.put("userId", user.getId());
        claims.put("roles", user.getRoles());
        return claims;
    }

}
