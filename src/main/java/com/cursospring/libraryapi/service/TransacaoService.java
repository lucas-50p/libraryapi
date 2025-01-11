package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.repository.AutorRepository;
import com.cursospring.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    /**
     * Essa class não faz parte do projeto principal
     */

    // Manager
    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("d5ee2e24-293d-4741-89e9-55f5b8431eac"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024,6,1));
    }

    @Transactional
    public void executar(){

        // Salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));

        autorRepository.save(autor);

        // Salva o Livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Teste Livro da Francisco");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        livro.setAutor(autor);
        livroRepository.save(livro);

        if(autor.getNome().equals("Teste Francisco")){
            throw new RuntimeException("Rollback!");
        }
    }
}
