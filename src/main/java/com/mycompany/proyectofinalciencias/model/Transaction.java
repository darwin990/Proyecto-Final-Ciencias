package com.mycompany.proyectofinalciencias.model;

public class Transaction {
    private final double amount;
    private final String source;
    private final int risk;
    private final long timestamp;
    private TransactionStatus status;

    public Transaction(double amount, String source) {
        this.amount = amount;
        this.source = source;
        this.risk = calculateRisk(source);
        this.timestamp = System.currentTimeMillis();
        this.status = TransactionStatus.PENDING;
    }

    private int calculateRisk(String source) {
        return switch (source.toLowerCase()) {
            case "iglesia" -> 20;
            case "casino" -> 40;
            case "constructora" -> 35;
            case "fundacion" -> 25;
            default -> 50;
        };
    }

    public enum TransactionStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }

    // Getters
    public double getAmount() { return amount; }
    public String getSource() { return source; }
    public int getRisk() { return risk; }
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
}