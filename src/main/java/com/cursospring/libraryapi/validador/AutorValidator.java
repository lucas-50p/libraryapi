package com.cursospring.libraryapi.validador;

import com.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    /**
     * Vantagens do uso de Optional
     * Evita NullPointerException: Ao usar Optional, fica explícito no código que um valor pode ser ausente, evitando o uso de null.
     * Legibilidade: O uso de métodos como ifPresent() e orElse() pode tornar o código mais legível e menos propenso a erros.
     * Quando usar Optional
     * Quando um valor pode estar ausente e você quer representar isso de forma clara.
     * Para evitar o uso de null em seu código, que pode levar a exceções e comportamento inesperado.
     */

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {

//        // Fui no banco busquei autor
//        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
//                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
//        );
//
//        // Verificar se é novo autor
//        if(autor.getId() == null){
//            return  autorEncontrado.isPresent();
//        }
//
//        // Não possui o mesmo ID
//        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();

        // Buscando autor no banco
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        // Se não encontrar nenhum autor com os mesmos dados, não existe um autor cadastrado
        if (autorEncontrado.isEmpty()) {
            return false;
        }

        // Se o autor não tem id (ou seja, é um novo autor), verifica se existe algum autor já cadastrado
        if (autor.getId() == null) {
            return true;  // Existe um autor com os mesmos dados, logo, não pode ser cadastrado
        }

        // Se o autor tem id, e o id encontrado não é o mesmo do autor atual, significa que já existe um autor com esses dados
        return !autor.getId().equals(autorEncontrado.get().getId());
    }
}
