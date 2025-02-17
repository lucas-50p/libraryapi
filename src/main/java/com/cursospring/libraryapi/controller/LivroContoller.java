package com.cursospring.libraryapi.controller;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.cursospring.libraryapi.controller.mappers.LivroMapper;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar (
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "20")
            Integer tamanhoPagina,
            PagedResourcesAssembler<ResultadoPesquisaLivroDTO> assembler
    ){
        Page <Livro> paginaResultado = livroService.pesquisa(
                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page <ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        // Retornando a resposta paginada
        return ResponseEntity.ok(resultado);
    }

//    @GetMapping
//    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar(
//            @RequestParam(value = "isbn", required = false) String isbn,
//            @RequestParam(value = "titulo", required = false) String titulo,
//            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
//            @RequestParam(value = "genero", required = false) GeneroLivro genero,
//            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
//            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
//            @RequestParam(value = "tamanho-pagina", defaultValue = "20") Integer tamanhoPagina) {
//
//        try {
//            // Logando os parâmetros recebidos
//            System.out.println("Parâmetros: isbn=" + isbn + ", titulo=" + titulo + ", nomeAutor=" + nomeAutor +
//                    ", genero=" + genero + ", anoPublicacao=" + anoPublicacao + ", pagina=" + pagina +
//                    ", tamanho-pagina=" + tamanhoPagina);
//
//            // Obter a página de livros a partir do serviço
//            Page<Livro> paginaResultado = livroService.pesquisa(
//                    isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
//
//            // Logando o resultado
//            System.out.println("Resultado da pesquisa: " + paginaResultado.getContent());
//
//            // Converter a página de livros para DTOs (ResultadoPesquisaLivroDTO)
//            Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(livroMapper::toDTO);
//
//            // Retornar a resposta paginada com os DTOs
//            return ResponseEntity.ok(resultado);
//        } catch (Exception e) {
//            // Logando a exceção
//            System.err.println("Erro ao processar a pesquisa: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null); // Você pode também retornar uma mensagem de erro específica
//        }
//    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto) {

        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = livroMapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
