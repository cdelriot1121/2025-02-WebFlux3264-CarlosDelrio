package com.parcial.dos.parcialdos.account.service;

import java.util.List;

import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;

public interface IAccountService {
    
    AccountResponseDTO create(AccountRequestDTO request);
    List<AccountResponseDTO> getAll();
    AccountResponseDTO getById(Long id);
    String update(Long id, AccountRequestDTO request);
    void delete(Long id);

    AccountOwnerBalanceDTO findByNumeroCuenta(String numeroCuenta);
}
