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

//    public  static Specification<Livro> anoPublicacao(Integer anoPublicacao){
//        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
//        return (root, query, cb) ->
//                cb.equal(cb.function("to_char", String.class,
//                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
//    }

    public static Specification<Livro> anoPublicacao(Integer anoPublicacao){
        // A comparação é feita somente com o ano, usando o to_char para garantir que estamos comparando somente o ano
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

}
