// src/main/java/com/mycompany/proyectofinalciencias/controller/GameInitializer.java
package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

public class GameInitializer {

    public static GameSession inicializarCampaña() {
        // Árbol base
        CorruptionNode presidente = new CorruptionNode("P01", "Presidente X", "Presidente");
        presidente.setWealth(1000);
        presidente.setInfluence(800);

        CorruptionTree arbol = new CorruptionTree(presidente);

        CorruptionNode ministro1 = new CorruptionNode("M01", "Ministro A", "Ministro");
        ministro1.setWealth(500);
        ministro1.setInfluence(300);

        CorruptionNode ministro2 = new CorruptionNode("M02", "Ministro B", "Ministro");
        ministro2.setWealth(450);
        ministro2.setInfluence(250);

        arbol.insert("P01", ministro1);
        arbol.insert("P01", ministro2);

        // Grafo externo
        ExternalGraph grafo = new ExternalGraph();
        grafo.addNode("P01", "Presidente X", "politico");
        grafo.addNode("M01", "Ministro A", "politico");
        grafo.addNode("M02", "Ministro B", "politico");
        grafo.addNode("F01", "Fiscalía", "institucion");

        grafo.addConnection("F01", "M01", 10);
        grafo.addConnection("F01", "M02", 8);
        grafo.addConnection("F01", "P01", 12);

        // Jugador
        PlayerStatus jugador = new PlayerStatus();

        // Eventos
        EventSystem eventos = new EventSystem();

        // Campaña
        Campaign campaign = new Campaign();

        return new GameSession(arbol, grafo, jugador, eventos, campaign);
    }
}
