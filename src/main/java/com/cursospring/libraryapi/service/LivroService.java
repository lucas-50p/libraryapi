package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ErrorResposta;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.repository.LivroRepository;
import com.cursospring.libraryapi.repository.specs.LivroSpeces;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro) {
        return  livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao){

        boolean temFiltro = isbn != null ||
                titulo != null ||
                genero != null ||
                anoPublicacao != null;

        if(!temFiltro){
            throw new IllegalArgumentException("Pelo menos um filtro deve ser informado.");
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

        return livroRepository.findAll();
    }
}
