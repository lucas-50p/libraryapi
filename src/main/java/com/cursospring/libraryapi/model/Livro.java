package com.cursospring.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data// Getter e set/ toString / EqualsAndHashCode / RequiredArgsConstructor
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 150, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    // TODO criar enumeração
    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero",  length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private String preco;
    // Relaciona
    @ManyToOne// Muitos Livros para um autor
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
