package com.br.itau.desafio.adapters.inbound.rest;

import com.br.itau.desafio.adapters.inbound.rest.dto.BalanceResponseDTO;
import com.br.itau.desafio.adapters.inbound.rest.mapper.ResponseMapper;
import com.br.itau.desafio.application.usecase.GetBalanceUseCase;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final ResponseMapper responseMapper;
    private final GetBalanceUseCase useCase;

    @GetMapping("/{accountId}")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable String accountId) {
        BalanceEntity balance = useCase.getBalance(accountId);
        BalanceResponseDTO responseBody = responseMapper.toBalanceResponse(balance);
        return ResponseEntity.ok(responseBody);
    }

}
