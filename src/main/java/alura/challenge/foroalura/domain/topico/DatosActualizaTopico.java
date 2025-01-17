package alura.challenge.foroalura.domain.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

//DTO para recibir los datos actualizados
public record DatosActualizaTopico(
        @NotNull
        Long idPost, //idPost del tópico
        String titulo,
        String mensaje,
        @JsonAlias("solucionado") boolean status //Para el caso donde haya un botón donde se pregunte si ya fue solucionado o no
) {
}
