package com.cursospring.libraryapi.controller.mappers;

import com.cursospring.libraryapi.controller.dto.UsuarioDTO;
import com.cursospring.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
