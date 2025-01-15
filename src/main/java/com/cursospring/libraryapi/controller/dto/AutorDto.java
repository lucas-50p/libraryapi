package com.cursospring.libraryapi.controller.dto;

import com.cursospring.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO - Data transfer object : transferencias de dados
 * que só recebe obj , da minha camada de repesentacao
 * do modelo representacional que é minha API faz parte do meu contrato,
 * mas não faz parte das outras camadas.
 */

//  Record só tem get class imutavel.
// Representacao do contrato.
// Camadada Represatacional.
public record AutorDto(
        UUID id,

        @NotBlank(message = "Campo obrigatorio")// String não venha nula e nem vazia
        String nome,

        @NotNull(message = "Campo obrigatorio")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatorio")
        String nacionalidade) {

    public Autor maperParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
