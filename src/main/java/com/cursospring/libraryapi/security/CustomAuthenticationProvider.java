package com.cursospring.libraryapi.security;

import com.cursospring.libraryapi.model.Usuario;
import com.cursospring.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrato = usuarioService.obterPorLogin(login);

        if(usuarioEncontrato == null){
            throw getErroUsuarioNaoEncontrato();
        }

        String senhaCriptografada = usuarioEncontrato.getSenha();

        boolean senhaBatem = passwordEncoder.matches(senhaDigitada, senhaCriptografada);

        if(senhaBatem){
            return new CustomAuthentication(usuarioEncontrato);
        }
        throw getErroUsuarioNaoEncontrato();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrato(){
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
