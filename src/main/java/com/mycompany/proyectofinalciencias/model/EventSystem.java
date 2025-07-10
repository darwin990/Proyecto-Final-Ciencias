package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class EventSystem {
    private Queue<String> eventQueue;
    private Random random;

    public EventSystem() {
        this.eventQueue = new LinkedList<>();
        this.random = new Random();
    }

    public void generarEventosAleatorios(CorruptionTree tree, PlayerStatus status) {
        int roll = random.nextInt(100);

        if (roll < 20) {
            eventQueue.add("Filtración a la prensa: reputación en riesgo.");
            status.reducirReputacion(10);
            status.aumentarSospecha(5);
        } else if (roll < 35) {
            eventQueue.add("Mega-Operación Anticorrupción: fiscales activos.");
            status.aumentarSospecha(8);
        } else if (roll < 50) {
            eventQueue.add("Traición dolorosa: posible pérdida de subordinados.");
            status.aumentarSospecha(6);
        } else if (roll < 65) {
            eventQueue.add("Elecciones Regionales: oportunidad o caos.");
            if (random.nextBoolean()) {
                status.aumentarReputacion(5);
            } else {
                status.reducirReputacion(5);
                status.aumentarSospecha(4);
            }
        } else if (roll < 75) {
            eventQueue.add("Cambio de Fiscal General: incertidumbre jurídica.");
            status.aumentarSospecha(3);
        } else if (roll < 85) {
            eventQueue.add("Presión internacional: aumento en costo de sobornos.");
            status.reducirReputacion(8);
        }
    }

    public void procesarEventos(CorruptionTree tree) {
        while (!eventQueue.isEmpty()) {
            String evento = eventQueue.poll();
            System.out.println("[EVENTO] " + evento);

            if (evento.contains("Filtración")) {
                for (CorruptionNode node : tree.traverseBFS()) {
                    if (node.getState().equals("bajo sospecha")) {
                        node.setState("investigado");
                    }
                }
            } else if (evento.contains("Traición")) {
                for (CorruptionNode node : tree.traverseBFS()) {
                    if (node.getState().equals("traidor")) {
                        for (CorruptionNode child : node.getChildren()) {
                            child.setParent(null);
                        }
                        node.getChildren().clear();
                    }
                }
            }
        }
    }
}
