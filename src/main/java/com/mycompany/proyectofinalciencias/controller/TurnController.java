// src/main/java/com/mycompany/proyectofinalciencias/controller/TurnController.java
package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

import java.util.List;

public class TurnController {

    private final GameSession session;
    private int turno = 1;

    public TurnController(GameSession session) {
        this.session = session;
    }

    public String ejecutarTurno() {
        StringBuilder log = new StringBuilder();
        log.append("\n----- TURNO ").append(turno).append(" -----\n");

        CorruptionTree arbol = session.getArbol();
        PlayerStatus jugador = session.getJugador();
        ExternalGraph grafo = session.getGrafo();
        EventSystem eventos = session.getEventos();
        MoneyLaunderingSystem launderingSystem = session.getLaunderingSystem();

        // Recolectar dinero sucio al inicio del turno
        launderingSystem.collectDirtyMoney(arbol);
        log.append("[DINERO SUCIO] Disponible para lavar: ")
           .append(String.format("%.2f", launderingSystem.getDirtyMoney()))
           .append("\n");

        arbol.ejecutarTurno();
        eventos.generarEventosAleatorios(arbol, jugador);
        eventos.procesarEventos(arbol);

        for (CorruptionNode nodo : arbol.traverseBFS()) {
            log.append(nodo.getId()).append(" - ")
               .append(nodo.getName()).append(" | Estado: ").append(nodo.getState())
               .append(" | Riqueza: ").append(nodo.getWealth())
               .append(" | Influencia: ").append(nodo.getInfluence()).append("\n");
        }

        if (jugador.getSospechaGlobal() >= 15) {
            if (jugador.realizarEncubrimiento(arbol, 50)) {
                log.append("[ENCUBRIMIENTO] Ejecutado exitosamente\n");
            } else {
                log.append("[ENCUBRIMIENTO] Fallido\n");
            }
        }

        // Actualizar IDs en la búsqueda de ruta
        List<String> ruta = grafo.dijkstraShortestPath("FIS01", "PER01");  // Actualizar IDs aquí
        double totalPeso = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            String a = ruta.get(i);
            String b = ruta.get(i + 1);
            for (ExternalGraph.Connection c : grafo.getConnections(a)) {
                if (c.getTarget().equals(b)) {
                    totalPeso += c.getWeight();
                    break;
                }
            }
        }
        if (totalPeso <= 15 && ruta.contains("PER01")) {  // Actualizar ID aquí también
            jugador.aumentarSospecha(10);
            log.append("[ESCÁNDALO] ¡Ruta crítica detectada! +10 sospecha\n");
        }

        // Activar habilidades especiales
        log.append("\n[HABILIDADES ESPECIALES]\n");
        for (CorruptionNode nodo : arbol.traverseBFS()) {
            if (nodo.getState().equals("activo") && nodo.getSpecialAbility() != null) {
                nodo.activarHabilidad(session);
                log.append("🎯 ").append(nodo.getName())
                   .append(" activó: ")
                   .append(nodo.getSpecialAbility().getNombre())
                   .append("\n");
            }
        }

        // Reducir cooldowns de habilidades
        for (CorruptionNode nodo : arbol.traverseBFS()) {
            nodo.reduceCooldown();
        }

        // Procesar transacciones de lavado pendientes
        session.getLaunderingSystem().processNextTransaction();
        log.append("[DINERO LIMPIO] Total lavado: ")
           .append(String.format("%.2f", launderingSystem.getCleanMoney()))
           .append("\n");

        if (jugador.estaPerdido()) {
            log.append("\n❌ Has perdido. La justicia actuó.\n");
        }

        // Notificar cambios
        session.notifyTreeUpdate();
        session.notifyPlayerUpdate();
        session.notifyEvent(new GameEvent("TURNO", log.toString(), GameEvent.EventSeverity.INFO));
        
        // Usar el campaignController del session en lugar de una variable no definida
        if (session.getCampaignController().verificarYAvanzar(arbol, jugador)) {
            session.notifyCampaignUpdate();
        }
        
        // Actualizar sistema de justicia
        session.getJusticeSystem().update();
        
        // Mostrar investigaciones activas
        List<Investigation> investigations = session.getJusticeSystem().getActiveInvestigations();
        if (!investigations.isEmpty()) {
            log.append("\n[INVESTIGACIONES ACTIVAS]\n");
            for (Investigation inv : investigations) {
                CorruptionNode target = arbol.findById(inv.getTargetId());
                log.append("📋 ").append(target.getName())
                   .append(" | Evidencia: ").append(String.format("%.1f", inv.getEvidence()))
                   .append("% | Turnos restantes: ").append(inv.getRemainingDuration())
                   .append("\n");
            }
        }

        turno++;
        return log.toString();
    }

    public int getTurnoActual() {
        return turno;
    }

    public boolean juegoTerminado() {
        return session.getJugador().estaPerdido() || session.getCampaignController().estaCompleta();
    }
}
