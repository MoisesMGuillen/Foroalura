package alura.challenge.foroalura.controller;

import alura.challenge.foroalura.domain.curso.Curso;
import alura.challenge.foroalura.domain.curso.CursoRepositorio;
import alura.challenge.foroalura.domain.topico.*;
import alura.challenge.foroalura.domain.usuario.Usuario;
import alura.challenge.foroalura.domain.usuario.UsuariosRepositorio;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    TopicosRepositorio topicosRepositorio;
    //Repositorios usados para obtener a los usuarios y los cursos
    @Autowired
    UsuariosRepositorio usuariosRepositorio;
    @Autowired
    CursoRepositorio cursoRepositorio;

    //Posteando un nuevo topico
    @PostMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaTopico> registraTopico(
            UriComponentsBuilder uriComponentsBuilder,
            //Recibiendo el post
            @RequestBody @Valid DatosRegistroTopico topico,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        //Buscando en la base de datos
        Optional<Usuario> usuario = usuariosRepositorio.buscarPorCorreo(userDetails.getUsername());
        Optional<Curso> curso = cursoRepositorio.findByNombre( topico.nombreCurso() );
        //Verificando si se puede subir o no
        if( usuario.isPresent() && curso.isPresent() ){
            Topico t = topicosRepositorio.save( new Topico( topico, usuario.get(), curso.get() ) );
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(t.getId()).toUri();
            return ResponseEntity.created(url).body( new DatosRespuestaTopico(t) );
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    //Obtener todos los topicos
    public ResponseEntity<Page<DatosRespuestaTopico>> muestraTopicos(
            @PageableDefault(
                    size = 10,
                    sort = {"curso","fechaCreacion"} //El atributo de orden debe ser el mismo al del atributo en la entidad
            ) Pageable paginacion
    ){
        return ResponseEntity.ok(topicosRepositorio.findAll(paginacion).map(DatosRespuestaTopico::new));
    }

    @GetMapping("/{id}")
    //Detallar un topico
    public ResponseEntity<DatosRespuestaTopico> detallaTopico(
            @PathVariable Long id
    ){
        Topico topico = topicosRepositorio.getReferenceById(id);
        System.out.println(topico.isStatus());
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @PutMapping("/actualizar")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @RequestBody @Valid DatosActualizaTopico datosActualizaTopico,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        //Extraer el usuario del repositorio usando los detalles del token
        Optional<Usuario> autor = usuariosRepositorio.buscarPorCorreo(userDetails.getUsername());
        //Buscando dicho tópico
        Topico topico = topicosRepositorio.getReferenceById(datosActualizaTopico.idPost());

        if( topico.getAutor() == autor.get()){
            topico.actualizarTopico(datosActualizaTopico);
            return ResponseEntity.ok(
                    new DatosRespuestaTopico(topico)
            );
        }else{
            throw new RuntimeException("El usuario no es el autor");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity eliminaTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        Topico topico = topicosRepositorio.getReferenceById(id);
        Optional<Usuario> usuario = usuariosRepositorio.buscarPorCorreo(userDetails.getUsername());

        if(
                topico.getAutor() == usuario.get()
        ){
            topicosRepositorio.delete(topico);
            return ResponseEntity.noContent().build();
        }else{
            throw new RuntimeException("El usuario no es el autor");
        }
    }

}