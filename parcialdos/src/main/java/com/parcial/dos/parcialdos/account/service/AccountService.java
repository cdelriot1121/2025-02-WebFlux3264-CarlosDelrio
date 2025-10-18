package com.parcial.dos.parcialdos.account.service;

import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.entity.Account;
import com.parcial.dos.parcialdos.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Account account = new Account();
        account.setAccountNumber(accountRequestDTO.getNumeroCuenta());
        account.setOwnerName(accountRequestDTO.getDueno());
        account.setBalance(accountRequestDTO.getBalanceActual());
        account.setActive(true);
        
        Account savedAccount = accountRepository.save(account);
        
        return mapToResponseDTO(savedAccount);
    }
    
    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<AccountResponseDTO> getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(this::mapToResponseDTO);
    }
    
    @Override
    public String updateAccountBalance(Long id, AccountRequestDTO accountRequestDTO) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            BigDecimal oldBalance = account.getBalance();
            account.setBalance(accountRequestDTO.getBalanceActual());
            accountRepository.save(account);
            
            return String.format("La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f", 
                    account.getAccountNumber(), oldBalance, accountRequestDTO.getBalanceActual());
        } else {
            return "Cuenta no encontrada";
        }
    }
    
    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
    
    @Override
    public Optional<AccountOwnerBalanceDTO> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(this::mapToOwnerBalanceDTO);
    }
    
    private AccountResponseDTO mapToResponseDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setNumeroCuenta(account.getAccountNumber());
        dto.setDueno(account.getOwnerName());
        dto.setBalanceActual(account.getBalance());
        dto.setActive(account.isActive());
        return dto;
    }
    
    private AccountOwnerBalanceDTO mapToOwnerBalanceDTO(Account account) {
        AccountOwnerBalanceDTO dto = new AccountOwnerBalanceDTO();
        dto.setDueno(account.getOwnerName());
        dto.setBalanceActual(account.getBalance());
        return dto;
    }
}