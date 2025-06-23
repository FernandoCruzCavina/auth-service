package com.bank.auth_service.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

/**
 * Configuration class for RSA-based JWT encoder and decoder beans using Nimbus JOSE + JWT.
 *
 * <p>
 * This class loads RSA public and private keys from application properties and uses them to:
 * </p>
 *
 * <ul>
 *   <li><b>Sign</b> JWT tokens with the private key (via {@link JwtEncoder})</li>
 *   <li><b>Verify</b> JWT signatures with the public key (via {@link JwtDecoder})</li>
 * </ul>
 *
 * <p><b>Note:</b> This configuration uses asymmetric cryptography (RSA) and the RS256 algorithm (SHA-256 with RSA signature).
 * The tokens are <i>signed</i> but not <i>encrypted</i> — anyone with the public key can verify the token, 
 * but its content is readable unless encrypted separately.</p>
 *
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */

@Configuration
public class KeyConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    /**
     * Creates a JwtDecoder bean using the configured RSA public key.
     *
     * @return a JwtDecoder for verifying JWT tokens
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    /**
     * Creates a JwtEncoder bean using the configured RSA public and private keys.
     *
     * @return a JwtEncoder for signing JWT tokens
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
