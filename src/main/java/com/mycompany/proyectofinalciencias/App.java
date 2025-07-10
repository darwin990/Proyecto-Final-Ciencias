// src/main/java/com/mycompany/proyectofinalciencias/App.java
package com.mycompany.proyectofinalciencias;

import com.mycompany.proyectofinalciencias.model.*;
import com.mycompany.proyectofinalciencias.controller.CampaignController;
import com.mycompany.proyectofinalciencias.view.MainFrame;

import javax.swing.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Nodo raíz
            CorruptionNode root = new CorruptionNode("P01", "Alcalde Principal", "Alcalde");
            root.setInfluence(20);
            root.setWealth(100);
            root.setLoyalty(70);
            root.setAmbition(50);
            root.setExposureRisk(30);

            CorruptionTree arbol = new CorruptionTree(root);

            // Grafo con conexiones
            ExternalGraph grafo = new ExternalGraph();
            grafo.agregarConexion("P01", "F01", 5);  // Conexión peligrosa
            grafo.agregarConexion("P01", "M01", 3);  // Medio amigable
            grafo.agregarConexion("F01", "A01", 7);

            // Jugador
            PlayerStatus jugador = new PlayerStatus();

            // Sistema de eventos (simulado)
            EventSystem eventos = new EventSystem();

            // Niveles de campaña
            CampaignLevel nivel1 = new CampaignLevel("Concejo Municipal", 1, 20);
            CampaignLevel nivel2 = new CampaignLevel("Gobernación", 3, 25);
            CampaignLevel nivel3 = new CampaignLevel("Congreso", 5, 30);
            Campaign campaign = new Campaign(Arrays.asList(nivel1, nivel2, nivel3));

            // Controlador
            CampaignController controller = new CampaignController(campaign);

            // Mostrar UI
            new MainFrame(arbol, grafo, jugador, eventos, controller);
        });
    }
}
