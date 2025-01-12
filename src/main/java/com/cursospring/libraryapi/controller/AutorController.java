package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.AutorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    // !!! Para testa na class application.

    // GET

    /**
     * Salvar autor - POST
     *
     * ResponseEntity - server para representar uma resposta.
     * RequestBody - server para indicar que parâmetro vai receber um obj JSON.
     * @return
     */
    @PostMapping// Atalho, não precisar colocar ação salvar
    // @RequestMapping(method = RequestMethod.POST)// Antigo
    public ResponseEntity salvar(@RequestBody AutorDto autorDto){
        return new ResponseEntity("Autor salvo com sucesso! " + autorDto, HttpStatus.CREATED);
    }

    // DELETE
    // PUT
}
