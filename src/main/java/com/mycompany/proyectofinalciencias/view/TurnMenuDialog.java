// File: com/mycompany/proyectofinalciencias/view/TurnMenuDialog.java
package com.mycompany.proyectofinalciencias.view;

import javax.swing.*;
import java.awt.*;

public class TurnMenuDialog extends JDialog {

    private String opcionSeleccionada = null;

    public TurnMenuDialog(JFrame parent) {
        super(parent, "Acciones Estratégicas", true);
        setLayout(new GridLayout(0, 1, 5, 5));

        JLabel title = new JLabel("Selecciona tu acción estratégica del turno:");
        add(title);

        JButton btnExpandir = new JButton("Expandir la red (Sobornar)");
        btnExpandir.addActionListener(e -> {
            opcionSeleccionada = "expandir";
            dispose();
        });

        JButton btnConsolidar = new JButton("Consolidar poder (Aumentar lealtad)");
        btnConsolidar.addActionListener(e -> {
            opcionSeleccionada = "consolidar";
            dispose();
        });

        JButton btnExtraer = new JButton("Extraer recursos (dinero/influencia)");
        btnExtraer.addActionListener(e -> {
            opcionSeleccionada = "extraer";
            dispose();
        });

        JButton btnEncubrimiento = new JButton("Operación de encubrimiento");
        btnEncubrimiento.addActionListener(e -> {
            opcionSeleccionada = "encubrimiento";
            dispose();
        });

        JButton btnNeutralizar = new JButton("Neutralizar amenaza (periodista/fiscal)");
        btnNeutralizar.addActionListener(e -> {
            opcionSeleccionada = "neutralizar";
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            opcionSeleccionada = "cancelar";
            dispose();
        });

        add(btnExpandir);
        add(btnConsolidar);
        add(btnExtraer);
        add(btnEncubrimiento);
        add(btnNeutralizar);
        add(btnCancelar);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getOpcionSeleccionada() {
        return opcionSeleccionada;
    }
}
