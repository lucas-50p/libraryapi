package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.AutorDTO;
import com.cursospring.libraryapi.controller.mappers.AutorMapper;
import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.Usuario;
import com.cursospring.libraryapi.security.SecurityService;
import com.cursospring.libraryapi.service.AutorService;
import com.cursospring.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController implements GenericController{

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    // !!! Para testa na class application.

    public AutorController(AutorService autorService, AutorMapper autorMapper){
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    /**
     * Salvar autor - POST
     *
     * ResponseEntity - server para representar uma resposta.
     * RequestBody - server para indicar que parâmetro vai receber um obj JSON.
     *
     * @Valid vai validar os campos
     * @return
     */
    @PostMapping// Atalho, não precisar colocar ação salvar
    @PreAuthorize("hasRole('GERENTE')")
    // @RequestMapping(method = RequestMethod.POST)// Antigo
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDto){
        Autor autor = autorMapper.toEntity(autorDto);
        autorService.salvar(autor);
        URI location = gerarheaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                        AutorDTO autorDto = autorMapper.toDTO(autor);
                        return ResponseEntity.ok(autorDto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    // Indompotente
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            // ID não encontrado 404
            return ResponseEntity.notFound().build();
        }

        // Deletado
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    /**
     * RequestParam - Parametro da requisão
     * Required = false ; campo não obrigatorio
     * se fosse obrigatorio não precisa colocar o value
     */
    //@GetMapping("/pesquisar")// não posso dois mapeamentos GET sejam repetidos
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        System.out.println("Nome: " + nome + " Nacionalidade: " + nacionalidade);
        // Lista de autor
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);

        // Transforma lista para DTO
        List<AutorDTO> lista = resultado
                .stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public  ResponseEntity<Void> atualizar
            (@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDto){
        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorDto.nome());
        autor.setNacionalidade(autorDto.nacionalidade());
        autor.setDataNascimento(autorDto.dataNascimento());

        autorService.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
