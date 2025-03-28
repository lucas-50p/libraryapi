package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.model.Usuario;
import com.cursospring.libraryapi.repository.LivroRepository;
import com.cursospring.libraryapi.repository.specs.LivroSpeces;
import com.cursospring.libraryapi.security.SecurityService;
import com.cursospring.libraryapi.validador.LivroValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Forma static para não precisar ficar colocando
import static com.cursospring.libraryapi.repository.specs.LivroSpeces.*;

@Service
@RequestMapping("livros")
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;
    private final SecurityService securityService;

    public LivroService(LivroRepository livroRepository, LivroValidator livroValidator, SecurityService securityService) {
        this.livroRepository = livroRepository;
        this.livroValidator = livroValidator;
        this.securityService = securityService;
    }

    public Livro salvar(Livro livro) {

        // Validar
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return  livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina){

        boolean temFiltro = isbn != null ||
                titulo != null ||
                genero != null ||
                nomeAutor != null ||
                anoPublicacao != null;

//        if(!temFiltro){
//            throw new IllegalArgumentException("Pelo menos um filtro deve ser informado.");
//        }

        // Retorna uma página vazia se não houver filtros
        if (!temFiltro) {
            return Page.empty(); // Retorna uma página vazia
        }

        // select * from livro where 0 = 0
        Specification<Livro> speces = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            // query = query and isbn = : isbn
            speces = speces.and(isbnEqual(isbn));
        }

        if(titulo != null){
            speces = speces.and(tituloLike(titulo));
        }

        if(genero != null){
            speces = speces.and(generoEqual(genero));
        }

        if(anoPublicacao != null){
            speces = speces.and(anoPublicacao(anoPublicacao));
        }

        if(nomeAutor != null){
            speces = speces.and(nomeAutorLike(nomeAutor));
        }

//        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        // Certifica-se que a página não será negativa e que o tamanho da página seja válido
        Pageable pageRequest = PageRequest.of(Math.max(pagina, 0), Math.max(tamanhoPagina, 1));

        //System.out.println("Query: " + speces);
        return livroRepository.findAll(speces, pageRequest);
    }

    public void atualizar(Livro livro) {

        if(livro.getId() == null || !livroRepository.existsById(livro.getId())){
            throw new IllegalArgumentException("Para atualizar, é necessario que o livro já esteja salvo!");
        }

        // Antes atualizr validar
        livroValidator.validar(livro);
        livroRepository.save(livro);

    }
}
