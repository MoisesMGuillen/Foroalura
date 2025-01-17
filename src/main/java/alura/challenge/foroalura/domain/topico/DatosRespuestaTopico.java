package alura.challenge.foroalura.domain.topico;

import java.time.LocalDate;

//DTO hecho para: 1. Detallar los topicos, 2.Enviarse por ResponseEntity
public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fechaCreacion,
        String status,
        String autor,
        String curso
) {
    public DatosRespuestaTopico(Topico topico){
        this( topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                ( topico.isStatus() ? "Resuelto" : "Sin resolver" ),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}