package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    //Page<Livro> findByAutor(Autor autor, Pageable pageable);

    // Query Method - Metodo de consulta - nome da propiedade
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // Pesquisa por titulo
    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = titulo
    Optional<Livro> findByIsbn(String isbn);

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

    /*
     * JPQL -> references as entidades e as propriedades
     * select l.* from livro as l order by l.titulo
     */
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdernadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id.autor
     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileiro'
        order by l.genero      
    """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados.
    @Query("select l from Livro l where l.genero = :genero order by :paramOrder")
    List<Livro> findByGenero(
            @Param("genero")GeneroLivro generoLivro,
            @Param("paramOrder") String nomePropiedade
            );

    // Pegar pela posição o parametro - positional parameters não precisa da anotação @param
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionParameters(GeneroLivro generoLivro, String nomePropiedade);

    /**
     * Deletar
     * @param genero
     *
     * !delete sempre coloque "WHERE"
     */
    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    /**
     * Atualizar
     * @param novaData
     *
     */
    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    // Existe livro cadastrado com esse autor
    boolean existsByAutor(Autor autor);
}
