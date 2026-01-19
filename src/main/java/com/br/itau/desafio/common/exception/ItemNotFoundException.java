package com.br.itau.desafio.common.exception;

/**
 * Exceção lançada quando um item não é encontrado no sistema.
 * Pode ser utilizada para recursos como contas, transações, etc.
 *
 * @since 2026-01-18
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Construtor com mensagem customizada.
     * @param message mensagem de erro
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe o nome do recurso e o id não encontrado.
     * @param name nome do recurso
     * @param id identificador do recurso
     */
    public ItemNotFoundException(String name, String id) {
        super(String.format("%s not found with id: %s", name, id));
    }

}
