// src/main/java/com/mycompany/proyectofinalciencias/view/MainMenu.java
package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.controller.GameInitializer;
import com.mycompany.proyectofinalciencias.model.GameMode;
import com.mycompany.proyectofinalciencias.model.GameSession;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Menú Principal");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnIniciar = new JButton("Iniciar Campaña");
        btnIniciar.addActionListener(e -> mostrarOpcionesInicializacion());

        add(btnIniciar, BorderLayout.CENTER);
        setVisible(true);
    }

    private void mostrarOpcionesInicializacion() {
        Object[] opciones = {"🌱 Árbol vacío", "🌳 Árbol aleatorio"};
        int seleccion = JOptionPane.showOptionDialog(
                this,
                "¿Cómo deseas iniciar la campaña?",
                "Inicialización del árbol",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == -1) return;

        boolean usarAleatorio = (seleccion == 1);
        GameSession session = GameInitializer.inicializar(GameMode.CAMPAÑA_SOLO, usarAleatorio);
        new MainFrame(session);
        dispose();
    }
}
