package java.com.geds.security;

import io.jsenwebtoken.*;
import io.jsenwebtoken.security.keys;
import org.springframework.beans.factory.annotation.value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwUtil{

    private final SecretKey key;
    private final long expirationMs;

    public JwUtil(
        @value ("${geds.jwt.secret}") String secret,
        @value ("${geds.jwt.expiration-ms}") long expiration){
            this.key = keys.hmacShakeyFor(secret.getBytes(StandardCharsets.UTF_8));
            this.expirationMs = expirationMs;
        }

        public String generarToken(String email, String rol){
            return  Jwts.builder()
            .subject(email)
            .claim("rol", rol)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis()+expirationMs))
            .sigWith(Key)
            .compact();
        }
        public String getEmail(String token) {
          return parseClaims(token).getSubjet();
        }

        public String getRol(String token){
              return parseClaims(token).get("rol", String.class);
        }

        public String validar(String token){
            try {
                parseClaims(token);
                return true;
            } catch ( JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        private Claims parseClaims(String token){
            return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).getPayload();
        }
}