// src/main/java/com/mycompany/proyectofinalciencias/view/MainFrame.java
package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.*;
import com.mycompany.proyectofinalciencias.controller.CampaignController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame extends JFrame implements GameStateObserver {

    private CampaignPanel campaignPanel;
    private CampaignController campaignController;

    private CorruptionTree arbol;
    private ExternalGraph grafo;
    private PlayerStatus jugador;
    private EventSystem eventos;

    private JTextArea estadoArea;
    private JButton turnoBtn, accionesBtn;

    private TreePanel treePanel;
    private GraphPanel graphPanel;
    private StatusPanel statusPanel;

    private int turno = 1;

    private GameSession session; // Agregar esta línea

    public MainFrame(GameSession session) {
        this.session = session;  // Guardar la referencia
        this.arbol = session.getArbol();
        this.grafo = session.getGrafo();
        this.jugador = session.getJugador();
        this.eventos = session.getEventos();
        this.campaignController = session.getCampaignController();

        // Registrarse como observador
        session.addObserver(this);

        this.treePanel = new TreePanel(arbol);
        this.graphPanel = new GraphPanel(grafo);
        this.statusPanel = new StatusPanel(session); // Cambiar esto
        this.campaignPanel = new CampaignPanel(campaignController.getCampaign());

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(treePanel);
        centerPanel.add(graphPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(campaignPanel, BorderLayout.NORTH);
        rightPanel.add(statusPanel, BorderLayout.CENTER);

        estadoArea = new JTextArea(10, 40);
        estadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(estadoArea);

        turnoBtn = new JButton("Siguiente Turno");
        turnoBtn.addActionListener(this::ejecutarTurno);

        accionesBtn = new JButton("Acciones del Jugador");
        accionesBtn.addActionListener(e -> 
            new TurnActionsDialog(this, session).setVisible(true)
        );



        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(accionesBtn);
        bottomPanel.add(turnoBtn);

        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Simulación Política - Modo Campaña");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ejecutarTurno(ActionEvent e) {
        estadoArea.append("\n----- TURNO " + turno + " -----\n");

        arbol.ejecutarTurno();
        eventos.generarEventosAleatorios(arbol, jugador);
        eventos.procesarEventos(arbol);

        for (CorruptionNode nodo : arbol.traverseBFS()) {
            estadoArea.append(nodo.getName() + " | Estado: " + nodo.getState()
                    + " | Riqueza: " + nodo.getWealth()
                    + " | Influencia: " + nodo.getInfluence() + "\n");
        }

        if (jugador.getSospechaGlobal() >= 15) {
            jugador.realizarEncubrimiento(arbol, 50);
            estadoArea.append("[ENCUBRIMIENTO] Ejecutado\n");
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
            estadoArea.append("[ESCÁNDALO] ¡Ruta de investigación crítica activa! +10 sospecha\n");
            jugador.aumentarSospecha(10);
        }

        turno++;
        actualizarEstado();

        if (jugador.estaPerdido()) {
            JOptionPane.showMessageDialog(this, "¡Has perdido! La justicia actuó.");
            turnoBtn.setEnabled(false);
            accionesBtn.setEnabled(false);
            return;
        }

        if (campaignController.verificarYAvanzar(arbol, jugador)) {
            campaignPanel.actualizar();

            if (campaignController.estaCompleta()) {
                new VictoryDialog(this).setVisible(true);
                turnoBtn.setEnabled(false);
                accionesBtn.setEnabled(false);
                return;
            }
        }

        treePanel.repaint();
        graphPanel.repaint();
        statusPanel.actualizar();
    }

    private void actualizarEstado() {
        estadoArea.append("Sospecha Global: " + jugador.getSospechaGlobal() + "\n");
        estadoArea.append("Reputación Pública: " + jugador.getReputacionPublica() + "\n");
        statusPanel.actualizar();
    }

    @Override
    public void onTreeUpdate() {
        treePanel.repaint();
    }

    @Override
    public void onPlayerStatusUpdate() {
        statusPanel.actualizar();
    }

    @Override
    public void onEventOccurred(GameEvent event) {
        mostrarEvento(event);
    }

    @Override
    public void onCampaignUpdate() {
        campaignPanel.actualizar();
    }

    private void mostrarEvento(GameEvent event) {
        SwingUtilities.invokeLater(() -> {
            String icon = switch(event.getSeverity()) {
                case INFO -> "ℹ️";
                case WARNING -> "⚠️";
                case CRITICAL -> "🚨";
            };
            
            estadoArea.append(icon + " " + event.getMessage() + "\n");
        });
    }
}
