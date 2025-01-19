package com.cursospring.libraryapi.controller.dto;

import com.cursospring.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        String dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDto autorDto
) {
}
