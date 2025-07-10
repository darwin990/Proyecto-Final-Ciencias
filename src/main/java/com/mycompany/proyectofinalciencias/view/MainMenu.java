// src/main/java/com/mycompany/proyectofinalciencias/view/MainMenu.java
package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.controller.GameInitializer;
import com.mycompany.proyectofinalciencias.model.GameMode;
import com.mycompany.proyectofinalciencias.model.GameSession;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Corruptópolis - Menú Principal");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con fondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Corruptópolis");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Botones con descripciones
        addGameModeButton(buttonPanel, "🎯 Modo Campaña",
                "Completa objetivos y alcanza el poder supremo",
                () -> iniciarModo(GameMode.CAMPAÑA));

        addGameModeButton(buttonPanel, "♾️ Imperio Infinito",
                "Juega sin límites ni objetivos específicos",
                () -> iniciarModo(GameMode.INFINITO));

        addGameModeButton(buttonPanel, "👥 Guerra de Carteles",
                "¡Próximamente! Compite contra otros jugadores",
                this::mostrarMensajeProximamente);

        addGameModeButton(buttonPanel, "❌ Salir",
                "Cerrar el juego",
                () -> System.exit(0));

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer con versión
        JLabel versionLabel = new JLabel("v1.0 - Proyecto Final Ciencias");
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(versionLabel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void addGameModeButton(JPanel panel, String title, String description, Runnable action) {
        JButton button = new JButton("<html><b>" + title + "</b><br/><small>" + description + "</small></html>");
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    private void iniciarModo(GameMode modo) {
        String mensaje = switch (modo) {
            case CAMPAÑA -> "Comienza tu ascenso al poder completando objetivos específicos.";
            case INFINITO -> "Construye tu imperio corrupto sin límites ni restricciones.";
            default -> "Selecciona cómo quieres iniciar el juego.";
        };

        Object[] opciones = {
                "🌱 Árbol vacío (Comenzar desde cero)",
                "🌳 Árbol aleatorio (Red preexistente)"
        };

        int seleccion = JOptionPane.showOptionDialog(
                this,
                mensaje,
                "Inicialización - " + modo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == -1) return;

        boolean usarAleatorio = (seleccion == 1);
        GameSession session = GameInitializer.inicializar(modo, usarAleatorio);
        new MainFrame(session);
        dispose();
    }

    private void mostrarMensajeProximamente() {
        JOptionPane.showMessageDialog(
                this,
                "El modo multijugador estará disponible próximamente.",
                "En desarrollo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
