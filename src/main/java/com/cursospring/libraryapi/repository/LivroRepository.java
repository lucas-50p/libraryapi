package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method - Metodo de consulta - nome da propiedade
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);
}
