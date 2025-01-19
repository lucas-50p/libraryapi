package com.cursospring.libraryapi.controller.dto;

import com.cursospring.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.util.UUID;

public record CadastroLivroDTO(

        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,

        @NotBlank(message = "campo obrigatorio")
        String titulo,

        @NotNull(message = "campo obrigatorio")
        @Past(message = "nao pode ser uma data futura")
        String dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,

        @NotNull(message = "campo obrigatorio")
        UUID idAutor
        ) {
}
