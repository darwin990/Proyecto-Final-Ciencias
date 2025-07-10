// src/main/java/com/mycompany/proyectofinalciencias/App.java
package com.mycompany.proyectofinalciencias;

import com.mycompany.proyectofinalciencias.model.*;
import com.mycompany.proyectofinalciencias.view.MainFrame;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Nodo raíz - Cambiar P01 por POL01
            CorruptionNode root = new CorruptionNode("POL01", "Alcalde Principal", "Alcalde");
            root.setInfluence(20);
            root.setWealth(100);
            root.setLoyalty(70);
            root.setAmbition(50);
            root.setExposureRisk(30);

            CorruptionTree arbol = new CorruptionTree(root);

            // Grafo externo - Usar prefijos descriptivos
            ExternalGraph grafo = new ExternalGraph();
            grafo.addNode("FIS01", "Fiscalía", "fiscal");      // F01 -> FIS01
            grafo.addNode("PER01", "Prensa", "periodista");    // P01 -> PER01
            grafo.addNode("CON01", "Contralor", "control");    // C01 -> CON01
            
            // Actualizar conexiones con nuevos IDs
            grafo.addConnection("FIS01", "PER01", 5);
            grafo.addConnection("FIS01", "CON01", 3);
            grafo.addConnection("PER01", "CON01", 4);

            // Jugador y eventos
            PlayerStatus jugador = new PlayerStatus();
            EventSystem eventos = new EventSystem();
            Campaign campaign = new Campaign();

            // Crear GameSession
            GameSession session = new GameSession(arbol, grafo, jugador, eventos, campaign);

            // Mostrar UI con GameSession
            new MainFrame(session);
        });
    }
}
