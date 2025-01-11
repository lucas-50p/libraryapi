package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> confirmar as alterações
     * Rollback -> desfazer as alterações
     */
    @Test
    //@Transactional// Não precisa pq o metodo executar já tem e um metodo public.
    void trasacaoSimples(){
        // Exemplos:
        // salvar um livro
        // salvar a autor
        // alugar o livro
        // enviar email pro locatário
        // notificar que o livro saiu da livraria
        transacaoService.executar();
    }
}
