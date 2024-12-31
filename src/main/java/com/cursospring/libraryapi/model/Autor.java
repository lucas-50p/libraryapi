package com.cursospring.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter // Get e set criado pelo Lombok
@Setter
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    // mappedBy - Vai dizer que essa entidade não tem essa coluna
    @OneToMany(mappedBy = "autor")// 1 autor para muitos livros
    private List<Livro> livros;

//    @Deprecated
//    public Autor() {
//        // Para uso do framework
//    }


//    public Autor(UUID id, String nacionalidade, String nome, LocalDate dataNascimento) {
//        this.id = id;
//        this.nacionalidade = nacionalidade;
//        this.nome = nome;
//        this.dataNascimento = dataNascimento;
//    }
}
