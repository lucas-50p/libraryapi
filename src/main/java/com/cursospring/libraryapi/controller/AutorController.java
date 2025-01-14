package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.AutorDto;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.service.AutorService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> salvar(@RequestBody AutorDto autorDto){

        try {

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

        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErrorResposta.conflito(e.getMessage());
            return  ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDto> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if(autorOptional.isPresent()){

            // Pegar as informações do autor e passa para o DTO.
            Autor autor = autorOptional.get();
            AutorDto autorDto = new AutorDto(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(autorDto);
        }
        // 404
        return ResponseEntity.notFound().build();
    }

    // Indompotente
    @DeleteMapping("{id}")
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
    @GetMapping("/pesquisar")// não posso dois mapeamentos GET sejam repetidos
    public ResponseEntity<List<AutorDto>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        // Lista de autor
        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);

        // Transforma lista para DTO
        List<AutorDto> lista = resultado
                .stream()
                .map(autor -> new AutorDto(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public  ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorDto autorDto){

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
