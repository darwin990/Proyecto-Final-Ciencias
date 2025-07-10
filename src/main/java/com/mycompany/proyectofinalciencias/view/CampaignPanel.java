package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.Campaign;

import javax.swing.*;
import java.awt.*;

public class CampaignPanel extends JPanel {

    private final Campaign campaign;
    private final JLabel lblNivel;
    private final JTextArea txtObjetivo;

    public CampaignPanel(Campaign campaign) {
        this.campaign = campaign;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Modo Campaña"));

        lblNivel = new JLabel("Nivel actual: " + campaign.nombreNivelActual());
        txtObjetivo = new JTextArea(campaign.objetivoActual());
        txtObjetivo.setEditable(false);
        txtObjetivo.setWrapStyleWord(true);
        txtObjetivo.setLineWrap(true);

        add(lblNivel, BorderLayout.NORTH);
        add(new JScrollPane(txtObjetivo), BorderLayout.CENTER);
    }

    public void actualizar() {
        lblNivel.setText("Nivel actual: " + campaign.nombreNivelActual());
        txtObjetivo.setText(campaign.objetivoActual());
    }
}
