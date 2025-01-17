package alura.challenge.foroalura.controller;

import alura.challenge.foroalura.domain.usuario.DatosAutenticacionUsuario;
import alura.challenge.foroalura.domain.usuario.Usuario;
import alura.challenge.foroalura.infra.security.DatosJWTToken;
import alura.challenge.foroalura.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller para recibir los datos de login
@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    //Endpoint para la autenticación
    @PostMapping
    public ResponseEntity autenticarUsuario(
            @RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario
    ){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.correo(),
                datosAutenticacionUsuario.contrasena()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken( (Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok( new DatosJWTToken(JWTtoken) );
    }

}