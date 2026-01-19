package com.br.itau.desafio.adapters.inbound.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.br.itau.desafio.common.exception.ItemNotFoundException;
import org.springframework.web.context.request.WebRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.br.itau.desafio.adapters.inbound.rest.dto.ErrorResponseDTO;

/**
 * Handler global para exceções da aplicação.
 * Responsável por capturar exceções específicas e retornar respostas padronizadas para o cliente.
 * Atualmente trata ItemNotFoundException, retornando status 404 e corpo padronizado.
 *
 * @since 2026-01-18
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura ItemNotFoundException e retorna resposta padronizada com status 404.
     *
     * @param ex exceção lançada quando o item não é encontrado
     * @param request contexto da requisição para obtenção do path
     * @return ResponseEntity com corpo de erro padronizado e status NOT_FOUND
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        String timestamp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime().toString();
        ErrorResponseDTO body = new ErrorResponseDTO(
            timestamp,
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}
