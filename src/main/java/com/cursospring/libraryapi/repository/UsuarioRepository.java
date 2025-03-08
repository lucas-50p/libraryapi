package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findBylogin(String login);
}
