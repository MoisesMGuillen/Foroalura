package alura.challenge.foroalura.domain.topico;
//Entidad para la base de datos

import alura.challenge.foroalura.domain.curso.Curso;
import alura.challenge.foroalura.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;

//JPA
@Entity
@Table(name="topicos")
//Lombok
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDate fechaCreacion;
    private boolean status;
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    private Curso curso;

    //Un constructor usado para una nueva instancia de tópicos
    public Topico(DatosRegistroTopico topico, Usuario autor, Curso curso){
        this.titulo = topico.titulo();
        this.mensaje = topico.mensaje();
        this.fechaCreacion = LocalDate.now();
        this.status=false;
        this.autor = autor;
        this.curso = curso;
    }

    public void actualizarTopico(@Valid DatosActualizaTopico datosActualizaTopico) {
        //Podrás cambiar el mensaje, titulo o si ya fue resuelto (solo el creador del post)
        if(datosActualizaTopico.titulo()!=null && !datosActualizaTopico.titulo().isEmpty()){
            this.titulo = datosActualizaTopico.titulo();
        }
        if(datosActualizaTopico.mensaje()!=null && !datosActualizaTopico.mensaje().isEmpty()){
            this.mensaje = datosActualizaTopico.mensaje();
        }
        if(datosActualizaTopico.status() != this.status ){
            this.status = true;
        }
    }
}