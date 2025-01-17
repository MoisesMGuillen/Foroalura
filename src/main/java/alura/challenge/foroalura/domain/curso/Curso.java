package alura.challenge.foroalura.domain.curso;

import alura.challenge.foroalura.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//Entidad para los cursos en la base de datos
@Table(name="cursos")
@Entity
//Anotaciones de Lombok
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    @OneToMany(mappedBy = "curso",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Topico> topico;

    public String getNombre() {
        return nombre;
    }
}