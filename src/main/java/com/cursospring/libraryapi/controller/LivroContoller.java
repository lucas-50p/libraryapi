package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
public class LivroContoller {

    private final LivroService livroService;

    public LivroContoller(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {

            // Mapear dto para entidade
            // Enviar a entidade para o service validar e salvar na base
            // Criar url para acesso dos dados do livro
            // Retornar codigo created com header location
            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
