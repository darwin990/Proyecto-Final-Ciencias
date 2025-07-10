// src/main/java/com/mycompany/proyectofinalciencias/controller/GameInitializer.java
package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;
import com.mycompany.proyectofinalciencias.util.RandomTreeGenerator;

public class GameInitializer {
    
    public static GameSession inicializar(GameMode modo, boolean usarArbolAleatorio) {
        switch (modo) {
            case CAMPAÑA:
                return inicializarCampaña(usarArbolAleatorio);
            case INFINITO:
                return inicializarModoInfinito(usarArbolAleatorio);
            case MULTIJUGADOR:
                throw new IllegalArgumentException("Modo multijugador aún no implementado");
            default:
                throw new IllegalArgumentException("Modo de juego no soportado");
        }
    }

    private static GameSession inicializarCampaña(boolean usarArbolAleatorio) {
        CorruptionTree arbol;
        if (usarArbolAleatorio) {
            arbol = RandomTreeGenerator.generar();
        } else {
            // Árbol base - Cambiamos P01 por POL01 (Político)
            CorruptionNode presidente = new CorruptionNode("POL01", "Presidente X", "Presidente");
            presidente.setWealth(1000);
            presidente.setInfluence(800);
            arbol = new CorruptionTree(presidente);
        }

        // Grafo externo - Usamos prefijos más descriptivos
        ExternalGraph grafo = new ExternalGraph();
        grafo.addNode("FIS01", "Fiscalía", "fiscal");      // F01 -> FIS01
        grafo.addNode("PER01", "Prensa", "periodista");    // P01 -> PER01
        grafo.addNode("CON01", "Contralor", "control");    // C01 -> CON01
        
        // Actualizar las conexiones con los nuevos IDs
        grafo.addConnection("FIS01", "PER01", 5);
        grafo.addConnection("FIS01", "CON01", 3);
        grafo.addConnection("PER01", "CON01", 4);

        // Jugador y eventos
        PlayerStatus jugador = new PlayerStatus();
        EventSystem eventos = new EventSystem();
        Campaign campaign = new Campaign();

        return new GameSession(arbol, grafo, jugador, eventos, campaign);
    }

    private static GameSession inicializarModoInfinito(boolean usarArbolAleatorio) {
        // Similar a inicializarCampaña pero sin Campaign
        GameSession session = inicializarCampaña(usarArbolAleatorio);
        session.getCampaignController().getCampaign().estaCompleta(); // Desactivar objetivos de campaña
        return session;
    }
}
