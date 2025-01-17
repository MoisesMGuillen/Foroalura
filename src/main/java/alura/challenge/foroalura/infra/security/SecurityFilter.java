package alura.challenge.foroalura.infra.security;


import alura.challenge.foroalura.domain.usuario.UsuariosRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Clase para encargarse del la cadena de filtros de seguridad
@Component //Estereotipo genérico de Spring para definir un componente
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Obtener la URL y el método del request
            String requestURI = request.getRequestURI();
            String requestMethod = request.getMethod();
            // Permitir todos los métodos GET y la ruta de login (POST)
            if ("GET".equalsIgnoreCase(requestMethod) || "/login".equals(requestURI)) {
                // Continuar con el siguiente filtro sin validar el token
                filterChain.doFilter(request, response);
                return;
            }

            // Obtener el token del encabezado Authorization
            var authHeader = request.getHeader("Authorization");
            // Validar si el token es nulo o vacío
            if (authHeader == null || authHeader.isEmpty()) {
                throw new RuntimeException("El token enviado no es válido");
            }

            // Lógica para verificar el token
            var token = authHeader.replace("Bearer ", "");
            var correoUsuario = tokenService.getSubject(token);
            if (correoUsuario != null) {
                var usuario = usuariosRepositorio.findByCorreoElectronico(correoUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );
                //Forzamos el inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // Continuar con el siguiente filtro
            filterChain.doFilter(request, response);
        }catch (RuntimeException ex) {
            // Manejo de la excepción en el filtro, de modo que se pueda enviar
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            response.getWriter().flush();
        }
    }

}