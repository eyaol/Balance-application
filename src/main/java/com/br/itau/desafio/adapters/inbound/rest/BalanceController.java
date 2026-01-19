package com.br.itau.desafio.adapters.inbound.rest;

import com.br.itau.desafio.adapters.inbound.rest.dto.ApiResponseDTO;
import com.br.itau.desafio.adapters.inbound.rest.mapper.ResponseMapper;
import com.br.itau.desafio.application.usecase.GetBalanceUseCase;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável por expor o endpoint de consulta de saldo.
 * Permite buscar o saldo de uma conta a partir do accountId informado na URL.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final ResponseMapper responseMapper;
    private final GetBalanceUseCase useCase;

    /**
     * Endpoint para consultar o saldo de uma conta pelo accountId.
     *
     * @param accountId identificador da conta a ser consultada
     * @return ResponseEntity contendo o saldo no formato ApiResponseDTO
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponseDTO> getBalance(@PathVariable String accountId) {
        BalanceEntity balance = useCase.getBalance(accountId);
        ApiResponseDTO responseBody = responseMapper.toBalanceResponse(balance);
        return ResponseEntity.ok(responseBody);
    }

}
