package com.cursospring.libraryapi.controller.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

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
public record AutorDTO(

        @Id
        UUID id,

        @NotBlank(message = "Campo obrigatorio")// String não venha nula e nem vazia
        @Size(min = 2, max = 100, message = "Campo fora fo tamanho padrão *") //Seguir como está no banoc
        String nome,

        @NotNull(message = "Campo obrigatorio *")
        //@Future// Data futura
        @Past // Data passada
        LocalDate dataNascimento,

        @NotBlank(message = "* Campo obrigatorio *")
        @Size(min = 2, max = 50, message = "Campo fora fo tamanho padrão *")
        String nacionalidade) {

//    public Autor maperParaAutor(){
//        Autor autor = new Autor();
//        autor.setNome(this.nome);
//        autor.setDataNascimento(this.dataNascimento);
//        autor.setNacionalidade(this.nacionalidade);
//        return autor;
//    }
}
