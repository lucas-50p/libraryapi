package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.model.Usuario;
import com.cursospring.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha((passwordEncoder.encode(senha)));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return usuarioRepository.findBylogin(login);
    }

    public Usuario obterPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
