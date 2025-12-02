package com.phellipe.barber_agenda_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.phellipe.barber_agenda_api.exception.TokenCreationException;
import com.phellipe.barber_agenda_api.model.user.Role;
import com.phellipe.barber_agenda_api.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("barber-booking-app-backend")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .withClaim("roles", user.getRoles()
                            .stream()
                            .map(Role::getRole)
                            .toList()
                    )
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Error while generating authentication token.");
        }

    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("barber-booking-app-backend")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return null;
        }

    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
