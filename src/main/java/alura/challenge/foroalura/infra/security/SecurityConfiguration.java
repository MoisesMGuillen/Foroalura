package alura.challenge.foroalura.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilita la protección CSRF, ya que generalmente no es necesaria en aplicaciones RESTful que no usan sesiones.
                .csrf(csrf -> csrf.disable())
                // Configura la política de manejo de sesiones como STATELESS. Esto significa que no se mantendrán sesiones en el servidor.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define las reglas de autorización para las solicitudes HTTP.
                .authorizeHttpRequests(req -> {

                    // Permitir todas las solicitudes GET
                    req.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    // Permite el acceso público al endpoint "/login" solo para solicitudes POST.
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers("/v3/api-docs/**","/swagger-ui.html","/swagger-html/**").permitAll();
                    // Exige autenticación para cualquier otra solicitud que no coincida con las reglas anteriores.
                    req.anyRequest().authenticated();
                })
                // Esta línea agregaría un filtro de seguridad personalizado
                // que se ejecutaría antes del filtro UsernamePasswordAuthenticationFilter.
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // Construye y devuelve la configuración de la cadena de filtros de seguridad.
                .build();
    }

    // Define un bean para el AuthenticationManager, el cual es responsable de gestionar la autenticación en la aplicación.
    @Bean
    // Se usa el objeto AuthenticationConfiguration proporcionado por Spring Security
    // para obtener una instancia configurada del AuthenticationManager.
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        // Obtiene y devuelve el AuthenticationManager configurado por Spring Security.
        // Este se utiliza para autenticar solicitudes, validando credenciales como nombre de usuario y contraseña.
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define un bean para el PasswordEncoder, que es responsable de codificar (encriptar) y verificar contraseñas.
    @Bean
    // Aquí se utiliza BCrypt, un algoritmo seguro de hash que incluye un factor de "salting" para dificultar ataques.
    public PasswordEncoder passwordEncoder(){
        // Devuelve una instancia de BCryptPasswordEncoder, que se usará para codificar contraseñas antes de guardarlas en la base de datos
        // y para comparar contraseñas en texto plano con las hashadas durante la autenticación.
        return new BCryptPasswordEncoder();
    }
}
