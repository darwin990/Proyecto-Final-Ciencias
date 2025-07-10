package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.controller.SimpleActionController;
import com.mycompany.proyectofinalciencias.model.*;

import javax.swing.*;
import java.awt.*;

public class TurnActionsDialog extends JDialog {

    private final SimpleActionController controller;

    public TurnActionsDialog(JFrame parent, PlayerStatus jugador, CorruptionTree arbol, ExternalGraph grafo) {
        super(parent, "Acciones del Turno", true);
        this.controller = new SimpleActionController(jugador, arbol, grafo);

        setLayout(new GridLayout(0, 1, 5, 5));

        add(createButton("Recolectar recursos", this::recolectarRecursos));
        add(createButton("Mejorar lealtad", this::mejorarLealtad));
        add(createButton("Agregar nuevo nodo", this::agregarNodo));
        add(createButton("Realizar encubrimiento", this::encubrir));
        add(createButton("Sabotear entidad externa", this::sabotear));
        add(createButton("Romper conexión externa", this::romperConexion));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        add(cerrar);

        setSize(350, 300);
        setLocationRelativeTo(parent);
    }

    private JButton createButton(String label, Runnable action) {
        JButton b = new JButton(label);
        b.addActionListener(e -> action.run());
        return b;
    }

    private void recolectarRecursos() {
        String resultado = controller.recolectarRecursos();
        JOptionPane.showMessageDialog(this, resultado);
    }

    private void mejorarLealtad() {
        String id = JOptionPane.showInputDialog(this, "ID del nodo a mejorar:");
        if (id != null) {
            String resultado = controller.mejorarLealtad(id);
            JOptionPane.showMessageDialog(this, resultado);
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
}
