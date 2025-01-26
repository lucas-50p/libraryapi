package com.cursospring.libraryapi.controller.mappers;

import com.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import com.cursospring.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

// Pegar dados do autor, pq entidade não tem autorDTO
@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    AutorMapper autorMapper;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(target = "autor", expression = "java(autorMapper.toDTO(livro.getAutor()))")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
