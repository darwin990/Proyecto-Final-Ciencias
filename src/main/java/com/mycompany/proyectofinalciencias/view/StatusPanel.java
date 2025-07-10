// src/main/java/com/mycompany/proyectofinalciencias/view/StatusPanel.java
package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.PlayerStatus;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel lblSospecha;
    private final JLabel lblReputacion;
    private final PlayerStatus jugador;

    public StatusPanel(PlayerStatus jugador) {
        this.jugador = jugador;
        this.setLayout(new GridLayout(2, 1));
        this.setBorder(BorderFactory.createTitledBorder("Estado del Jugador"));

        lblSospecha = new JLabel();
        lblReputacion = new JLabel();

        add(lblSospecha);
        add(lblReputacion);

        actualizar();
    }

    public void actualizar() {
        lblSospecha.setText("Sospecha Global: " + jugador.getSospechaGlobal());
        lblReputacion.setText("Reputación Pública: " + jugador.getReputacionPublica());
    }
}
