package com.parcial.dos.parcialdos.account.dto;


public class AccountOwnerBalanceDTO {
    private String dueno;
    private Double balanceActual;
    
    // Constructor vacío
    public AccountOwnerBalanceDTO() {
    }
    
    // Constructor con parámetros
    public AccountOwnerBalanceDTO(String dueno, Double balanceActual) {
        this.dueno = dueno;
        this.balanceActual = balanceActual;
    }
    
    // Getters y setters
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
}
