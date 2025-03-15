package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.Usuario;
import com.cursospring.libraryapi.repository.AutorRepository;
import com.cursospring.libraryapi.repository.LivroRepository;
import com.cursospring.libraryapi.security.SecurityService;
import com.cursospring.libraryapi.validador.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Camada de dominio da aplicação
@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public AutorService(AutorRepository autorRepository,
                        AutorValidator autorValidator,
                        LivroRepository livroRepository, SecurityService securityService){
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
        this.securityService = securityService;
    }

    public Autor salvar(Autor autor){

        // Validar
        autorValidator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor){

        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja na base!");
        }
        // Validar
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        
        if(possuiLivroCadastrados(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Autor possui livros cadastrados!");
        }
        
        autorRepository.delete(autor);
    }

    // Se existe Livro para autor não é permitido a exclusão.
    private boolean possuiLivroCadastrados(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){

        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null){
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();

        // Verifique se o nome ou nacionalidade não são nulos antes de definir
        if (nome != null) autor.setNome(nome);
        if (nacionalidade != null) autor.setNacionalidade(nacionalidade);

        // Ajuste do ExampleMatcher
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // Ignorar maiúsculas/minúsculas
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Correspondência parcial

        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }
}
