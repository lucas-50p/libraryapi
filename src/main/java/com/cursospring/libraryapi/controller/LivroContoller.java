package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.cursospring.libraryapi.controller.mappers.LivroMapper;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
public class LivroContoller implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroContoller(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // Mapear dto para entidade
        Livro livro = livroMapper.toEntity(dto);

        // Enviar a entidade para o service validar e salvar na base
        Livro livroSalvo = livroService.salvar(livro);
        var url = gerarheaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( ()-> ResponseEntity.notFound().build() );
    }
}
