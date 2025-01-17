package alura.challenge.foroalura.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

//Repositorio de los usuarios para buscar a los usuarios registrados en la base de datos
public interface UsuariosRepositorio extends JpaRepository<Usuario, Long> {
    UserDetails findByCorreoElectronico(String username);
    @Query("SELECT u FROM Usuario u WHERE u.correoElectronico = :correo")
    Optional<Usuario> buscarPorCorreo(@Param("correo") String correo);
}