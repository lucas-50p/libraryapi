package com.cursospring.libraryapi.controller.dto;

import java.time.LocalDate;

/**
 * DTO - Data transfer object : transferencias de dados
 * que só recebe obj , da minha camada de repesentacao
 * do modelo representacional que é minha API faz parte do meu contrato,
 * mas não faz parte das outras camadas.
 */

// Representacao do contrato
public record AutorDto(
        String nome,
        LocalDate datanascimento,
        String nacionalidade) {



}
