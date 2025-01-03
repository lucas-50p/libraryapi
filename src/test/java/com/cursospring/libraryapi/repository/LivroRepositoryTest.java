package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO_CIENTIFICA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        // Encontra o id do autor
        Autor autor = autorRepository
                .findById(UUID.fromString("8bc0137f-a870-40f4-abfd-6ffa39a4a265"))
                .orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    /*
     * Melhor opção essa manual
     */
    @Test
    void salvarAutorELivroTest(){

        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO_CIENTIFICA);
        livro.setTitulo("Harry Potter");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));

        // Salvar o autor
        autorRepository.save(autor);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    // Us
    @Test
    void salvarCascadeTest(){

        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO_CIENTIFICA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorLivro(){

        // O "ID" do Livro
        UUID idLivro = UUID.fromString("afec625c-e257-4e02-a419-f24391c496e6");
        var livroParaAtualizar = livroRepository.findById(idLivro).orElse(null);

        // Vai procurar do ID do autor
        UUID idAutor = UUID.fromString("8bc0137f-a870-40f4-abfd-6ffa39a4a265");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);
        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID idLivro = UUID.fromString("afec625c-e257-4e02-a419-f24391c496e6");
        livroRepository.deleteById(idLivro);
    }
}