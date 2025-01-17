package alura.challenge.foroalura.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//Entidad de los perfiles de cada usuario
@Table(name="perfiles")
@Entity
//Anotaciones de Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Perfil {
    @Id
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "perfil",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PerfilUsuario> usuarios;
}