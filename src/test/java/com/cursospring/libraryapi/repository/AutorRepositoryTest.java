package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria B");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("e6629e41-a114-4165-a5e3-bef4f1fe248b");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1980,1,30));
            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listaTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    /*
     * Contagem de autores
     */
    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletePorIdTeste(){
        var id = UUID.fromString("fa855951-2da7-4db0-94cb-66d3d11e9dea");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTeste(){
        var id = UUID.fromString("8478cc73-aa98-4ba8-9c61-eee3e4163349");
        var maria = autorRepository.findById(id).get();
        autorRepository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("J. K. Rowling");
        autor.setNacionalidade("Inglaterra");
        autor.setDataNascimento(LocalDate.of(1970, 8, 4));

        Livro livro = new Livro();
        livro.setIsbn("20847-8485");
        livro.setPreco(BigDecimal.valueOf(50));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Harry potter - Pedra Filosofal");
        livro.setDataPublicacao(LocalDate.of(1999, 1,2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("20947-8485");
        livro2.setPreco(BigDecimal.valueOf(75));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setTitulo("Harry potter - Calice de Fogo");
        livro2.setDataPublicacao(LocalDate.of(2005, 1,2));
        livro2.setAutor(autor);

        // Inicializar lista
        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        // Salva autor
        autorRepository.save(autor);

        // Para salvar uma lista
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    //@Transactional
    void listarLivrosAutor(){
        var id= UUID.fromString("8bc0137f-a870-40f4-abfd-6ffa39a4a265");
        var autor = autorRepository.findById(id).get();

        // Buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
