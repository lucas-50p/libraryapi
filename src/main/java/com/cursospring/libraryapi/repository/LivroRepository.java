package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method - Metodo de consulta - nome da propiedade
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // Pesquisa por titulo
    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = titulo
    List<Livro> findByIsbn(String isbn);

    /*
     * "E"
     * select * from livro where titulo = ? and preco = ?
     */
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    /*
     * "OU"
     * select * from livro where titulo = ? or isbn = ?
     */
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    /*
     * select * from livro where data_publicacao between ? and ?
     */
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);
}
