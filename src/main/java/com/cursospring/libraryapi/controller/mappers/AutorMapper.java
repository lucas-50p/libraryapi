package com.cursospring.libraryapi.controller.mappers;

import com.cursospring.libraryapi.controller.dto.AutorDTO;
import com.cursospring.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO autorDto);

    AutorDTO toDTO(Autor autor);
}
