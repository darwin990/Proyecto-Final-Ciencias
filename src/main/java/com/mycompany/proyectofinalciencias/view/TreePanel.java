package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.CorruptionNode;
import com.mycompany.proyectofinalciencias.model.CorruptionTree;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TreePanel extends JPanel {

    private CorruptionTree tree;

    public TreePanel(CorruptionTree tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        CorruptionNode root = tree.getRoot();
        if (root != null) {
            drawNode(g, root, getWidth() / 2, 40, getWidth() / 4);
        }
    }

    private void drawNode(Graphics g, CorruptionNode node, int x, int y, int offset) {
        g.setColor(getColorByState(node.getState()));
        g.fillOval(x - 30, y - 20, 60, 40);

        g.setColor(Color.BLACK);
        g.drawOval(x - 30, y - 20, 60, 40);

        // Mostrar "ID - Nombre"
        String label = node.getId() + " - " + node.getName();
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        g.drawString(label, x - textWidth / 2, y + 5);

        List<CorruptionNode> children = node.getChildren();
        int childCount = children.size();
        int i = 0;
        for (CorruptionNode child : children) {
            int childX = x - offset + (i * (offset * 2 / Math.max(childCount - 1, 1)));
            int childY = y + 80;

            g.setColor(Color.BLACK);
            g.drawLine(x, y + 20, childX, childY - 20);

            drawNode(g, child, childX, childY, offset / 2);
            i++;
        }
    }

    private Color getColorByState(String state) {
        return switch (state.toLowerCase()) {
            case "investigado" -> Color.RED;
            case "bajo sospecha" -> Color.ORANGE;
            case "traidor" -> Color.GRAY;
            case "activo" -> Color.GREEN;
            default -> Color.LIGHT_GRAY;
        };
    }
}
