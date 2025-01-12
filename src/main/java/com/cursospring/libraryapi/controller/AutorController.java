package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.AutorDto;
import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;

    // !!! Para testa na class application.

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    /**
     * Salvar autor - POST
     *
     * ResponseEntity - server para representar uma resposta.
     * RequestBody - server para indicar que parâmetro vai receber um obj JSON.
     * @return
     */
    @PostMapping// Atalho, não precisar colocar ação salvar
    // @RequestMapping(method = RequestMethod.POST)// Antigo
    public ResponseEntity<Void> salvar(@RequestBody AutorDto autorDto){

        Autor autorEntidade = autorDto.maperParaAutor();
        autorService.salvar(autorEntidade);

        // URL localizado onde foi creado.
        // http://localhost:8080/autores/id
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()// Vai pegar essa requisição autal URL
                .path("/{id}")// "/" precisa do barra para separar url
                .buildAndExpand(autorEntidade.getId())// Parametro
                .toUri();// Vai transformar obj em URI

        // Antigo
        //return new ResponseEntity("Autor salvo com sucesso! " + autorDto, HttpStatus.CREATED);

        // Novo
        return ResponseEntity.created(location).build();
    }

}
