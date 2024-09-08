package br.com.fiap.wavemail.domain.service.auth;

import br.com.fiap.wavemail.domain.dto.auth.TokenDto;
import br.com.fiap.wavemail.domain.model.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${secret.key}")
    private String secret;


    public TokenDto generateToken(UserEntity user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT
                    .create()
                    .withIssuer("wavemail")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpireDate())
                    .sign(algorithm);

            return new TokenDto(user.getId(), user.getEmail(), token);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("JWT generation failed", exception);
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("wavemail")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("JWT validation failed");
        }
    }



    public Instant generateExpireDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }
}
