package com.cursospring.libraryapi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarheaderLocation(UUID id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    // URL localizado onde foi creado.
    // http://localhost:8080/autores/id
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()// Vai pegar essa requisição autal URL
//                .path("/{id}")// "/" precisa do barra para separar url
//                .buildAndExpand(autor.getId())// Parametro
//                .toUri();// Vai transformar obj em URI
}
