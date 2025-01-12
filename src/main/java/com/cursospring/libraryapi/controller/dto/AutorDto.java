package com.cursospring.libraryapi.controller.dto;

import com.cursospring.libraryapi.model.Autor;

import java.time.LocalDate;

/**
 * DTO - Data transfer object : transferencias de dados
 * que só recebe obj , da minha camada de repesentacao
 * do modelo representacional que é minha API faz parte do meu contrato,
 * mas não faz parte das outras camadas.
 */

//  Record só tem get class imutavel.
// Representacao do contrato
public record AutorDto(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    public Autor maperParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
