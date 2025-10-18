package com.parcial.dos.parcialdos.account.service;

import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    
    List<AccountResponseDTO> getAllAccounts();
    
    Optional<AccountResponseDTO> getAccountById(Long id);
    
    String updateAccountBalance(Long id, AccountRequestDTO accountRequestDTO);
    
    void deleteAccount(Long id);
    
    Optional<AccountOwnerBalanceDTO> findByAccountNumber(String accountNumber);
}
