package com.parcial.dos.parcialdos.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.service.IAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final IAccountService service;

    public AccountController(IAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO request) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody AccountRequestDTO request) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<AccountOwnerBalanceDTO> getByNumeroCuenta(@PathVariable String numeroCuenta) {
        return null;
    }
}
