package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.controller.SimpleActionController;
import com.mycompany.proyectofinalciencias.model.*;

import javax.swing.*;
import java.awt.*;

public class TurnActionsDialog extends JDialog {
    private final SimpleActionController controller;
    private final GameSession session;
    private int accionesRestantes = 2; // Límite de acciones por turno
    private final JLabel lblAccionesRestantes;

    public TurnActionsDialog(JFrame parent, GameSession session) {
        super(parent, "Acciones del Turno", true);
        this.session = session;
        this.controller = new SimpleActionController(
            session.getJugador(),
            session.getArbol(),
            session.getGrafo()
        );

        setLayout(new GridLayout(0, 1, 5, 5));

        // Agregar contador de acciones
        lblAccionesRestantes = new JLabel("Acciones restantes: " + accionesRestantes);
        lblAccionesRestantes.setHorizontalAlignment(SwingConstants.CENTER);
        lblAccionesRestantes.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblAccionesRestantes, 0); // Añadir al inicio

        add(createButton("💰 Sobornar nodo", this::sobornarNodo));     // Agregar esta línea
        add(createButton("Recolectar recursos", this::recolectarRecursos));
        add(createButton("Mejorar lealtad", this::mejorarLealtad));
        add(createButton("Agregar nuevo nodo", this::agregarNodo));
        add(createButton("Realizar encubrimiento", this::encubrir));
        add(createButton("Sabotear entidad externa", this::sabotear));
        add(createButton("Romper conexión externa", this::romperConexion));
        add(createButton("Lavar dinero", this::mostrarOpcionesLavado));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        cerrar.setEnabled(false); // Deshabilitar botón de cerrar para forzar usar todas las acciones
        add(cerrar);

        setSize(350, 300);
        setLocationRelativeTo(parent);
    }

    private void ejecutarAccion(Runnable action) {
        if (accionesRestantes > 0) {
            action.run();
            accionesRestantes--;
            lblAccionesRestantes.setText("Acciones restantes: " + accionesRestantes);
            
            if (accionesRestantes == 0) {
                dispose(); // Cerrar diálogo automáticamente
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No quedan acciones disponibles para este turno",
                "Límite alcanzado",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private JButton createButton(String label, Runnable action) {
        JButton b = new JButton(label);
        b.addActionListener(e -> ejecutarAccion(action));
        return b;
    }

    private void recolectarRecursos() {
        String resultado = controller.recolectarRecursos();
        JOptionPane.showMessageDialog(this, resultado);
        
        // Notificar cambios para actualizar la UI
        session.notifyPlayerUpdate();  // Actualiza status panel
        session.notifyTreeUpdate();    // Actualiza árbol
    }

    private void mejorarLealtad() {
        String id = JOptionPane.showInputDialog(this, "ID del nodo a mejorar:");
        if (id != null) {
            String resultado = controller.mejorarLealtad(id);
            JOptionPane.showMessageDialog(this, resultado);
            
            // Notificar cambios
            session.notifyTreeUpdate();
            session.notifyPlayerUpdate();
        }
    }

    private void agregarNodo() {
        String idPadre = JOptionPane.showInputDialog(this, "ID del nodo padre:");
        if (idPadre == null) return;

        String id = JOptionPane.showInputDialog(this, "ID nuevo:");
        String nombre = JOptionPane.showInputDialog(this, "Nombre:");
        String rol = JOptionPane.showInputDialog(this, "Rol:");
        String sobornoStr = JOptionPane.showInputDialog(this, "Costo de soborno:");

        if (id == null || nombre == null || rol == null || sobornoStr == null) return;

        try {
            double soborno = Double.parseDouble(sobornoStr);

            CorruptionNode nuevo = new CorruptionNode(id, nombre, rol);
            nuevo.setBribeCost(soborno);
            nuevo.setInfluence(Math.random() * 20);
            nuevo.setWealth(Math.random() * 50);
            nuevo.setLoyalty((int) (40 + Math.random() * 40));
            nuevo.setAmbition((int) (20 + Math.random() * 50));
            nuevo.setExposureRisk((int) (20 + Math.random() * 50));

            String resultado = controller.agregarNodo(idPadre, nuevo);
            JOptionPane.showMessageDialog(this, resultado);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Costo inválido.");
        }
    }

    private void encubrir() {
        String resultado = controller.encubrir();
        JOptionPane.showMessageDialog(this, resultado);
    }

    private void sabotear() {
        String id = JOptionPane.showInputDialog(this, "ID del nodo externo a sabotear:");
        if (id != null) {
            String resultado = controller.sabotear(id);
            JOptionPane.showMessageDialog(this, resultado);
        }
    }

    private void romperConexion() {
        String a = JOptionPane.showInputDialog(this, "ID nodo A:");
        String b = JOptionPane.showInputDialog(this, "ID nodo B:");
        if (a != null && b != null) {
            String resultado = controller.romperConexion(a, b);
            JOptionPane.showMessageDialog(this, resultado);
        }
    }

    private void mostrarOpcionesLavado() {
        // Primero recolectar dinero sucio
        session.getLaunderingSystem().collectDirtyMoney(session.getArbol());
        
        // Mostrar cantidad disponible
        double disponible = session.getLaunderingSystem().getDirtyMoney();
        if (disponible <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "No hay dinero sucio disponible para lavar",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String[] fuentes = {"Iglesia", "Casino", "Constructora", "Fundación"};
        String fuente = (String) JOptionPane.showInputDialog(
            this,
            String.format("Dinero sucio disponible: %.2f\nSeleccione el medio para lavar:", disponible),
            "Lavar Dinero",
            JOptionPane.QUESTION_MESSAGE,
            null,
            fuentes,
            fuentes[0]
        );

        if (fuente != null) {
            String montoStr = JOptionPane.showInputDialog(
                this,
                String.format("Ingrese la cantidad a lavar (máximo %.2f):", disponible),
                "Lavar Dinero",
                JOptionPane.QUESTION_MESSAGE
            );

            try {
                double monto = Double.parseDouble(montoStr);
                if (monto > disponible) {
                    JOptionPane.showMessageDialog(
                        this,
                        "No puede lavar más dinero del disponible",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                session.getLaunderingSystem().queueTransaction(monto, fuente.toLowerCase());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Monto inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Agregar este nuevo método
    private void sobornarNodo() {
        String id = JOptionPane.showInputDialog(this, "ID del nodo a sobornar:");
        if (id != null) {
            String resultado = controller.sobornarNodo(id);
            JOptionPane.showMessageDialog(this, resultado);
            
            // Notificar cambios
            session.notifyTreeUpdate();
            session.notifyPlayerUpdate();
        }
    }
}
