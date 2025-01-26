package com.cursospring.libraryapi.repository.specs;

import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpeces {

    public  static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        // Upper (livro.titulo) like (%:param%)
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public  static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }
}
