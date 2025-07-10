package com.mycompany.proyectofinalciencias.view;

import javax.swing.*;
import java.awt.*;

public class VictoryDialog extends JDialog {
    public VictoryDialog(JFrame parent) {
        super(parent, "¡Campaña Completada!", true);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("🎉 ¡Felicidades! Has completado todos los niveles de corrupción con éxito.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setSize(450, 150);
        setLocationRelativeTo(parent);
    }
}
