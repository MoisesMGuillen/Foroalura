package alura.challenge.foroalura.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

//Repositorio para subir los tópicos subidos
public interface TopicosRepositorio extends JpaRepository<Topico, Long> {
}
