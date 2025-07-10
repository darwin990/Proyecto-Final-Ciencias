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
        CampaignController campaignController = session.getCampaignController();

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

        List<String> ruta = grafo.dijkstraShortestPath("F01", "P01");
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
        if (totalPeso <= 15 && ruta.contains("P01")) {
            jugador.aumentarSospecha(10);
            log.append("[ESCÁNDALO] ¡Ruta crítica detectada! +10 sospecha\n");
        }

        if (jugador.estaPerdido()) {
            log.append("\n❌ Has perdido. La justicia actuó.\n");
        }

        // Notificar cambios
        session.notifyTreeUpdate();
        session.notifyPlayerUpdate();
        session.notifyEvent(new GameEvent("TURNO", log.toString(), GameEvent.EventSeverity.INFO));
        
        if (campaignController.verificarYAvanzar(arbol, jugador)) {
            session.notifyCampaignUpdate();
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
