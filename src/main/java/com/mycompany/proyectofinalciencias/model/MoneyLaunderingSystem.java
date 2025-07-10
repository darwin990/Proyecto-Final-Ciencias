package com.mycompany.proyectofinalciencias.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MoneyLaunderingSystem {
    private final Queue<Transaction> transactionQueue;
    private double cleanMoney;
    private double dirtyMoney;
    private final Random random;
    private final GameSession gameSession;

    public MoneyLaunderingSystem(GameSession gameSession) {
        this.transactionQueue = new LinkedList<>();
        this.cleanMoney = 0;
        this.dirtyMoney = 0;
        this.random = new Random();
        this.gameSession = gameSession;
    }

    public void queueTransaction(double amount, String source) {
        if (amount > dirtyMoney) {
            gameSession.notifyEvent(new GameEvent(
                "LAVADO", 
                "No hay suficiente dinero sucio para lavar", 
                GameEvent.EventSeverity.WARNING
            ));
            return;
        }

        Transaction transaction = new Transaction(amount, source);
        transactionQueue.offer(transaction);
        dirtyMoney -= amount;
        
        gameSession.notifyEvent(new GameEvent(
            "LAVADO", 
            String.format("Transacción de %.2f añadida a través de %s", amount, source),
            GameEvent.EventSeverity.INFO
        ));
    }

    public void processNextTransaction() {
        if (transactionQueue.isEmpty()) return;

        Transaction transaction = transactionQueue.poll();
        transaction.setStatus(Transaction.TransactionStatus.PROCESSING);

        // Chance de éxito basado en el riesgo
        boolean success = random.nextInt(100) > transaction.getRisk();

        if (success) {
            cleanMoney += transaction.getAmount() * 0.9; // 10% de pérdida por el lavado
            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            
            gameSession.notifyEvent(new GameEvent(
                "LAVADO",
                String.format("Lavado exitoso de %.2f a través de %s", 
                            transaction.getAmount(), 
                            transaction.getSource()),
                GameEvent.EventSeverity.INFO
            ));
        } else {
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            // Cambiar increaseSuspicion por aumentarSospecha
            gameSession.getJugador().aumentarSospecha(15);
            
            gameSession.notifyEvent(new GameEvent(
                "LAVADO",
                "¡Transacción sospechosa detectada! Aumenta el nivel de sospecha",
                GameEvent.EventSeverity.WARNING
            ));
        }
    }

    public void addDirtyMoney(double amount) {
        this.dirtyMoney += amount;
    }

    public void collectDirtyMoney(CorruptionTree tree) {
        for (CorruptionNode node : tree.traverseBFS()) {
            if (node.getState().equals("activo")) {
                double amount = node.getWealth() * 0.2; // Toma 20% de la riqueza como dinero sucio
                node.setWealth(node.getWealth() - amount);
                this.dirtyMoney += amount;
            }
        }
        
        gameSession.notifyEvent(new GameEvent(
            "LAVADO", 
            String.format("Se recolectó %.2f en dinero sucio de la red", dirtyMoney),
            GameEvent.EventSeverity.INFO
        ));
    }

    // Getters
    public double getCleanMoney() { return cleanMoney; }
    public double getDirtyMoney() { return dirtyMoney; }
    public int getPendingTransactions() { return transactionQueue.size(); }
}