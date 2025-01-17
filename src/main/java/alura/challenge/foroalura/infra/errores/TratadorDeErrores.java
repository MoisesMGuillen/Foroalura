package alura.challenge.foroalura.infra.errores;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TratadorDeErrores {
    //Error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    //Error 400 - Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(
            MethodArgumentNotValidException e
    ){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    //Error 403
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarError403(
            RuntimeException e
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

        //DTO para mantener registro de los errores
    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    //Error 500
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<DatosErrorInternalServerError> tratarError500(
            HttpServerErrorException.InternalServerError ex
    ){
        var error = new DatosErrorInternalServerError(
                "Error interno del servidor",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.internalServerError().body(error);
    }
      //DTO para recibir los errores de error interno del servidor
    private record DatosErrorInternalServerError(
            String mensaje,
            String detalle,
            LocalDateTime timeStamp
    ){}

}