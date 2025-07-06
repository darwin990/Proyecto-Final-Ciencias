/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias;

/**
 *
 * @author moral
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphVisualizer extends JFrame {
    private Map<String, List<String>> graph;
    private GraphPanel graphPanel;

    public GraphVisualizer(Map<String, List<String>> graph) {
        this.graph = graph;
        setTitle("Visualizador de Grafos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        graphPanel = new GraphPanel(graph);
        add(new JScrollPane(graphPanel), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refrescar Vista");
        refreshButton.addActionListener(e -> graphPanel.refreshGraph());
        add(refreshButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Ejemplo de grafo representado como diccionario
        Map<String, List<String>> sampleGraph = new HashMap<>();
        sampleGraph.put("A", Arrays.asList("B", "C"));
        sampleGraph.put("B", Arrays.asList("A", "D", "E"));
        sampleGraph.put("C", Arrays.asList("A", "F"));
        sampleGraph.put("D", Arrays.asList("B"));
        sampleGraph.put("E", Arrays.asList("B", "F"));
        sampleGraph.put("F", Arrays.asList("C", "E"));

        SwingUtilities.invokeLater(() -> {
            GraphVisualizer visualizer = new GraphVisualizer(sampleGraph);
            visualizer.setVisible(true);
        });
    }
}

class GraphPanel extends JPanel {
    private Map<String, List<String>> graph;
    private Map<String, Point> nodePositions;
    private static final int NODE_RADIUS = 20;
    private static final int PADDING = 50;

    public GraphPanel(Map<String, List<String>> graph) {
        this.graph = graph;
        this.nodePositions = new HashMap<>();
        setPreferredSize(new Dimension(1000, 800));
        calculateNodePositions();
    }

    private void calculateNodePositions() {
        nodePositions.clear();
        int centerX = getPreferredSize().width / 2;
        int centerY = getPreferredSize().height / 2;
        int radius = Math.min(getPreferredSize().width, getPreferredSize().height) / 3;

        // Distribuir nodos en un círculo
        List<String> nodes = new ArrayList<>(graph.keySet());
        double angleIncrement = 2 * Math.PI / nodes.size();

        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            double angle = i * angleIncrement;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            nodePositions.put(node, new Point(x, y));
        }
    }

    public void refreshGraph() {
        calculateNodePositions();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar las aristas primero
        g2d.setColor(Color.LIGHT_GRAY);
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String source = entry.getKey();
            Point sourcePos = nodePositions.get(source);

            if (sourcePos != null) {
                for (String target : entry.getValue()) {
                    Point targetPos = nodePositions.get(target);
                    if (targetPos != null) {
                        g2d.drawLine(sourcePos.x, sourcePos.y, targetPos.x, targetPos.y);
                    }
                }
            }
        }

        // Dibujar los nodos después
        for (Map.Entry<String, Point> entry : nodePositions.entrySet()) {
            Point pos = entry.getValue();
            String node = entry.getKey();

            // Dibujar círculo del nodo
            g2d.setColor(Color.WHITE);
            g2d.fillOval(pos.x - NODE_RADIUS, pos.y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
            g2d.setColor(Color.BLUE);
            g2d.drawOval(pos.x - NODE_RADIUS, pos.y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

            // Dibujar etiqueta del nodo
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node);
            int textHeight = fm.getHeight();
            g2d.drawString(node, pos.x - textWidth / 2, pos.y + textHeight / 4);
        }
    }
}