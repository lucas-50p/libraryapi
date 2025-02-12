package com.cursospring.libraryapi.validador;

import com.cursospring.libraryapi.exceptions.CampoInvalidoException;
import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.repository.LivroRepository;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public LivroValidator(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void validar(Livro livro) throws RegistroDuplicadoException{

        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("Preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatorio!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
