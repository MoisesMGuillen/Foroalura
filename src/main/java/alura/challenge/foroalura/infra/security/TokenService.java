package alura.challenge.foroalura.infra.security;

import alura.challenge.foroalura.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("alura")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {

        if(token == null){
            throw new RuntimeException();
        }

        DecodedJWT verifier = null;
        try {
            // Configurar el algoritmo de firma para validar el token
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            // Construye un verificador JWT con validaciones específicas
            verifier = JWT.require(algorithm)
                    .withIssuer("alura") // Validar que el token tiene el issuer esperado
                    .build() // Construye el verificador
                    .verify(token); // Valida el token contra la firma, issuer, etc.

            // Extrae el subject del token validado
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            // Manejo de errores si la verificación falla
            System.out.println(exception.toString());
        }

        // Valida que el token tenga un subject válido
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier inválido");
        }

        // Regresa el subject del token
        return verifier.getSubject();
    }

    // Para que el token expire 2 horas después
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant( ZoneOffset.of("-05:00") );
    }
}
