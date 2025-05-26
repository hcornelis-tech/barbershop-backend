package nl.fontys.s3.clipfadeapplication.configuration.security.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidCredentialsException;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessToken;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenDecoder, AccessTokenEncoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accessToken.getRoles())) {
            claimsMap.put("roles", accessToken.getRoles());
        }

        if (accessToken.getUserId() != 0) {
            claimsMap.put("user_id", accessToken.getUserId());
        }

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(accessToken.getUserEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            List<String> roles = claims.get("roles", List.class);
            int userId = claims.get("user_id", Integer.class);

            return new AccessTokenImpl(claims.getSubject(), userId, roles);
        }
        catch (JwtException e) {
            throw new InvalidCredentialsException(e.getMessage());
        }
    }
}
