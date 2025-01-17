package alura.challenge.foroalura.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

//Clase intermedia entre Usuario y Perfil, permitiendo al usuario mantener más de un perfil entre sí
@Table(name="usuario_perfil")
@Entity
//Anotaciones de Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"usuario","perfil"})
public class PerfilUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Aunque no necesite un idPost, se debe poner. Igual, en la base de datos no habrá problema
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}
