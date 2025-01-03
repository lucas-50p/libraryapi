package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutoRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria B");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("e6629e41-a114-4165-a5e3-bef4f1fe248b");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1980,1,30));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listaTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    /*
     * Contagem de autores
     */
    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTeste(){
        var id = UUID.fromString("fa855951-2da7-4db0-94cb-66d3d11e9dea");
        repository.deleteById(id);
    }

    @Test
    public void deleteTeste(){
        var id = UUID.fromString("8478cc73-aa98-4ba8-9c61-eee3e4163349");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
}
