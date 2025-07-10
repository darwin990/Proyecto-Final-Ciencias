package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.ExternalGraph;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GraphPanel extends JPanel {

    private ExternalGraph graph;
    private String pathStartId = "F01";
    private String pathEndId = "P01";

    public GraphPanel(ExternalGraph graph) {
        this.graph = graph;
        setBackground(Color.WHITE);
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Map<String, ExternalGraph.ExternalNode> nodes = graph.getNodes();
    List<String> ids = new ArrayList<>(nodes.keySet());

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    int radius = Math.min(centerX, centerY) - 80;

    Map<String, Point> positions = new HashMap<>();
    for (int i = 0; i < ids.size(); i++) {
        double angle = 2 * Math.PI * i / ids.size();
        int x = centerX + (int) (radius * Math.cos(angle));
        int y = centerY + (int) (radius * Math.sin(angle));
        positions.put(ids.get(i), new Point(x, y));
    }

    // Calcular ruta crítica
    List<String> path = graph.dijkstraShortestPath(pathStartId, pathEndId);

    // Dibujar aristas
    for (String from : ids) {
        for (ExternalGraph.Connection conn : graph.getConnections(from)) {
            String to = conn.getTarget();
            if (positions.containsKey(to) && from.compareTo(to) < 0) {
                Point p1 = positions.get(from);
                Point p2 = positions.get(to);

                boolean isInPath = isConnectionInPath(from, to, path);
                g.setColor(isInPath ? Color.RED : Color.BLACK);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);

                int mx = (p1.x + p2.x) / 2;
                int my = (p1.y + p2.y) / 2;
                g.setColor(Color.BLUE);
                g.drawString(String.format("%.0f", conn.getWeight()), mx, my);
            }
        }
    }

    // Dibujar nodos
    for (String id : ids) {
        ExternalGraph.ExternalNode node = nodes.get(id);
        Point pos = positions.get(id);
        g.setColor(getColorByType(node.getType()));
        g.fillOval(pos.x - 20, pos.y - 20, 40, 40);
        g.setColor(Color.BLACK);
        g.drawOval(pos.x - 20, pos.y - 20, 40, 40);
        g.drawString(node.getLabel(), pos.x - 30, pos.y + 35);
    }
}


    private Color getColorByType(String type) {
        return switch (type.toLowerCase()) {
            case "fiscal" -> Color.RED;
            case "periodista" -> Color.ORANGE;
            case "politico" -> Color.GREEN;
            default -> Color.GRAY;
        };
    }
    private boolean isConnectionInPath(String a, String b, List<String> path) {
    for (int i = 0; i < path.size() - 1; i++) {
        String n1 = path.get(i);
        String n2 = path.get(i + 1);
        if ((n1.equals(a) && n2.equals(b)) || (n1.equals(b) && n2.equals(a))) {
            return true;
        }
    }
    return false;
}

}
