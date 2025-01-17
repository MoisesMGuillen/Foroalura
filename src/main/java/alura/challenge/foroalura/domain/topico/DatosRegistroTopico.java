package alura.challenge.foroalura.domain.topico;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Record cuyo uso es recibir los datos del topico del Postman
public record DatosRegistroTopico(
        @NotEmpty
        String mensaje,
        @NotEmpty
        String nombreCurso,
        @NotEmpty
        String titulo
) {
}