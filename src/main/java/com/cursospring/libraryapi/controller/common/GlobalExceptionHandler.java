package com.cursospring.libraryapi.controller.common;

import com.cursospring.libraryapi.controller.dto.ErrorCampo;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.OperationsException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestControllerAdvice// Vai capturar as exceptions
public class GlobalExceptionHandler {

//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)//Ele capturar o erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)// FIXO
    public ErrorResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErrorCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros
        );
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErrorResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErrorResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResposta handleErrosNaoTratados(RuntimeException e){
        return new ErrorResposta(
              HttpStatus.INTERNAL_SERVER_ERROR.value(),
              "Ocorreu um erro inseperado. Contado Adm",
              List.of());
    }
}
