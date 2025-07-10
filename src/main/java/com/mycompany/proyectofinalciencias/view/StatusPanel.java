// src/main/java/com/mycompany/proyectofinalciencias/view/StatusPanel.java
package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.PlayerStatus;
import com.mycompany.proyectofinalciencias.model.GameSession;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel lblSospecha;
    private final JLabel lblReputacion;
    private final JLabel lblDineroSucio;
    private final JLabel lblDineroLimpio;
    private final PlayerStatus jugador;
    private final GameSession session;

    public StatusPanel(GameSession session) {
        this.session = session;
        this.jugador = session.getJugador();
        this.setLayout(new GridLayout(4, 1));
        this.setBorder(BorderFactory.createTitledBorder("Estado del Jugador"));

        lblSospecha = new JLabel();
        lblReputacion = new JLabel();
        lblDineroSucio = new JLabel();
        lblDineroLimpio = new JLabel();

        add(lblSospecha);
        add(lblReputacion);
        add(lblDineroSucio);
        add(lblDineroLimpio);

        actualizar();
    }

    public void actualizar() {
        lblSospecha.setText("Sospecha Global: " + jugador.getSospechaGlobal());
        lblReputacion.setText("Reputación Pública: " + jugador.getReputacionPublica());
        lblDineroSucio.setText(String.format("Dinero Sucio: %.2f", session.getLaunderingSystem().getDirtyMoney()));
        lblDineroLimpio.setText(String.format("Dinero Lavado: %.2f", session.getLaunderingSystem().getCleanMoney()));
    }
}
