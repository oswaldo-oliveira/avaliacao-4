package br.com.compass.avaliacao4.handler;

import br.com.compass.avaliacao4.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class handlerException extends ResponseEntityExceptionHandler {

    private static final String STATE_NOT_FOUND = "Objeto não encontrado.";
    private static final String ERRO_INTERNO_NO_SERVIDOR = "Erro interno no servidor.";
    private static final String IDEOLOGIA_INVALIDA = "Ideologia inválida.";
    private static final String CARGO_INVALIDO = "Cargo inválido.";
    private static final String SEXO_INVALIDO = "Sexo inválido.";
    private static final String DATA_INVALIDA = "Data inválida.";

    @ExceptionHandler(value = PartidoNotFoundException.class)
    protected ResponseEntity<MensagemErro> handlerPartidoNotFound(PartidoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemErro(STATE_NOT_FOUND));
    }

    @ExceptionHandler(value = AssociadoNotFoundException.class)
    protected ResponseEntity<MensagemErro> handlerAssociadoNotFound(AssociadoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemErro(STATE_NOT_FOUND));
    }

    @ExceptionHandler(value = SexoNotFoundException.class)
    protected ResponseEntity<MensagemErro> handlerSexoNotFound(SexoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErro(SEXO_INVALIDO));
    }

    @ExceptionHandler(value = CargoPoliticoNotFoundException.class)
    protected ResponseEntity<MensagemErro> handlerSexoNotFound(CargoPoliticoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErro(CARGO_INVALIDO));
    }

    @ExceptionHandler(value = IdeologiaNotFoundException.class)
    protected ResponseEntity<MensagemErro> handlerIdeologiaNotFound(IdeologiaNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErro(IDEOLOGIA_INVALIDA));
    }

    @ExceptionHandler(value = DataInvaledException.class)
    protected ResponseEntity<MensagemErro> handlerDataNotFound(DataInvaledException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErro(DATA_INVALIDA));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<MensagemErro> handlerExceptionGlobal(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensagemErro(ERRO_INTERNO_NO_SERVIDOR));
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
                "O campo: " + fieldError.getField() +
                " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemErro(validationList));
    }
}