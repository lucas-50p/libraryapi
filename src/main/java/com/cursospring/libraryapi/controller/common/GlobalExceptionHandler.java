package com.cursospring.libraryapi.controller.common;

import com.cursospring.libraryapi.controller.dto.ErrorCampo;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice// Vai capturar as exceptions
public class GlobalExceptionHandler {

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
}
