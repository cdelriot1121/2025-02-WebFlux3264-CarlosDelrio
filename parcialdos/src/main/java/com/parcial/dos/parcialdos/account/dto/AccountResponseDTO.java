package com.parcial.dos.parcialdos.account.dto;

public class AccountResponseDTO {
    private Long id;
    private String numeroCuenta;
    private String dueno;
    private Double balanceActual;
    private boolean active;
    
    // Constructor vacío
    public AccountResponseDTO() {
    }
    
    // Constructor completo
    public AccountResponseDTO(Long id, String numeroCuenta, String dueno, Double balanceActual, boolean active) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.dueno = dueno;
        this.balanceActual = balanceActual;
        this.active = active;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getDueno() {
        return dueno;
    }
    
    public void setDueno(String dueno) {
        this.dueno = dueno;
    }
    
    public Double getBalanceActual() {
        return balanceActual;
    }
    
    public void setBalanceActual(Double balanceActual) {
        this.balanceActual = balanceActual;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}