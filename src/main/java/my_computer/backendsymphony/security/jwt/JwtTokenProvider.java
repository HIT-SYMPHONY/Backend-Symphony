package my_computer.backendsymphony.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration_time}")
    private Integer EXPIRATION_TIME_ACCESS_TOKEN_MINUTES;

    @Value("${jwt.refresh.expiration_time}")
    private Integer EXPIRATION_TIME_REFRESH_TOKEN_MINUTES;

    public String generateToken(UserPrincipal userPrincipal, boolean isRefreshToken) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder()
                .subject(userPrincipal.getId())
                .issuer("hitsymphony.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .claim("username", userPrincipal.getUsername())
                .claim("scope", buildScope(userPrincipal));
        if (isRefreshToken) {
            claimsSetBuilder.expirationTime(new Date(
                    Instant.now().plus(EXPIRATION_TIME_REFRESH_TOKEN_MINUTES, ChronoUnit.MINUTES).toEpochMilli()
            ));
        } else {
            claimsSetBuilder.expirationTime(new Date(
                    Instant.now().plus(EXPIRATION_TIME_ACCESS_TOKEN_MINUTES, ChronoUnit.MINUTES).toEpochMilli()
            ));
        }

        JWTClaimsSet jwtClaimsSet = claimsSetBuilder.build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new RuntimeException("Token is invalid or expired");
        }

        return signedJWT;
    }

    private String buildScope(UserPrincipal userPrincipal) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}