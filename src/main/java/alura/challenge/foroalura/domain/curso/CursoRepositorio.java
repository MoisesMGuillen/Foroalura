package alura.challenge.foroalura.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Un repositorio para encontrar los cursos en la base de datos
public interface CursoRepositorio extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNombre(String nombre);
}
