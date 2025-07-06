/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinalciencias;

/**
 *
 * @author moral
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaryTreeVisualizer extends JFrame {
    private NaryTree tree;
    private TreePanel treePanel;
    private Random random = new Random();
    private JLabel statusLabel;

    public NaryTreeVisualizer() {
        setTitle("Árbol N-ario con Nodos Botón");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear árbol con raíz inicial
        tree = new NaryTree(50); // Valor inicial para la raíz

        // Panel para dibujar el árbol
        treePanel = new TreePanel(tree);
        JScrollPane scrollPane = new JScrollPane(treePanel);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de controles
        JPanel controlPanel = new JPanel(new GridLayout(1, 4));
        
        JButton addButton = new JButton("Agregar Nodo Aleatorio");
        addButton.addActionListener(e -> {
            int newValue = random.nextInt(100);
            int position = random.nextInt(4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha
            
            switch(position) {
                case 0: tree.addNodeAboveRoot(newValue); break;
                case 1: tree.addChildToRoot(newValue); break;
                case 2: tree.addNodeLeftToRoot(newValue); break;
                case 3: tree.addNodeRightToRoot(newValue); break;
            }
            
            treePanel.updateTreeUI();
        });
        
        JButton zoomInButton = new JButton("Zoom In (+)");
        zoomInButton.addActionListener(e -> {
            treePanel.zoomIn();
        });
        
        JButton zoomOutButton = new JButton("Zoom Out (-)");
        zoomOutButton.addActionListener(e -> {
            treePanel.zoomOut();
        });
        
        JButton resetButton = new JButton("Reset View");
        resetButton.addActionListener(e -> {
            treePanel.resetView();
        });
        
        controlPanel.add(addButton);
        controlPanel.add(zoomInButton);
        controlPanel.add(zoomOutButton);
        controlPanel.add(resetButton);
        
        // Barra de estado
        statusLabel = new JLabel(" Zoom: 100% | Haga clic en los nodos para ver detalles");
        add(statusLabel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NaryTreeVisualizer visualizer = new NaryTreeVisualizer();
            visualizer.setVisible(true);
        });
    }
}

class TreePanel extends JPanel {
    private NaryTree tree;
    private double scale = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private Point dragStart;
    
    public TreePanel(NaryTree tree) {
        this.tree = tree;
        setLayout(null); // Usamos layout absoluto para posicionar los botones
        setPreferredSize(new Dimension(2000, 2000));
        setOpaque(true);
        setBackground(Color.WHITE);
        
        // Listeners para el zoom y desplazamiento
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
                setCursor(Cursor.getDefaultCursor());
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    offsetX += dx;
                    offsetY += dy;
                    
                    // Mover todos los componentes (botones)
                    for (Component comp : getComponents()) {
                        Point loc = comp.getLocation();
                        comp.setLocation(loc.x + dx, loc.y + dy);
                    }
                    
                    dragStart = e.getPoint();
                    repaint();
                }
            }
        });
        
        addMouseWheelListener(e -> {
            double zoomFactor = 1.1;
            if (e.getWheelRotation() > 0) {
                scale /= zoomFactor;
            } else {
                scale *= zoomFactor;
            }
            
            scale = Math.max(0.1, Math.min(scale, 5.0));
            updateStatus();
            updateTreeUI();
        });
        
        // Dibujar el árbol inicial
        updateTreeUI();
    }
    
    public void updateTreeUI() {
        // Limpiar todos los componentes existentes
        removeAll();
        
        if (tree.getRoot() != null) {
            // Dibujar el árbol comenzando desde la raíz
            drawTree(tree.getRoot(), getWidth() / 2, 50, getWidth() / 4);
        }
        
        revalidate();
        repaint();
    }
    
    private void drawTree(Node node, int x, int y, int xOffset) {
        // Crear botón para el nodo
        JButton nodeButton = new JButton(Integer.toString(node.value));
        nodeButton.setBounds(
            (int)((offsetX + x - 20) * scale),
            (int)((offsetY + y - 20) * scale),
            (int)(40 * scale),
            (int)(40 * scale)
        );
        nodeButton.addActionListener(e -> showNodeDetails(node));
        nodeButton.setBackground(Color.WHITE);
        nodeButton.setForeground(Color.BLACK);
        nodeButton.setFocusPainted(false);
        nodeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(nodeButton);

        // Dibujar los hijos
        if (!node.children.isEmpty()) {
            int childCount = node.children.size();
            int startX = x - (childCount - 1) * xOffset / 2;
            int childY = y + 80;

            for (int i = 0; i < childCount; i++) {
                Node child = node.children.get(i);
                int childX = startX + i * xOffset;

                // Calcular puntos de inicio y fin de la línea
                int parentCenterX = (int)((offsetX + x) * scale) + (int)(20 * scale);
                int parentBottomY = (int)((offsetY + y + 20) * scale);
                int childTopX = (int)((offsetX + childX) * scale) + (int)(20 * scale);
                int childTopY = (int)((offsetY + childY - 20) * scale);

                // Crear panel para la línea de conexión
                JLayeredPane linePanel = new JLayeredPane() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(
                            parentCenterX - this.getX(),
                            parentBottomY - this.getY(),
                            childTopX - this.getX(),
                            childTopY - this.getY()
                        );
                    }
                };

                // Calcular límites del panel de línea
                int minX = Math.min(parentCenterX, childTopX);
                int minY = Math.min(parentBottomY, childTopY);
                int maxX = Math.max(parentCenterX, childTopX);
                int maxY = Math.max(parentBottomY, childTopY);

                linePanel.setBounds(
                    minX,
                    minY,
                    maxX - minX + 1,
                    maxY - minY + 1
                );

                add(linePanel, JLayeredPane.DEFAULT_LAYER);

                // Dibujar el hijo recursivamente
                drawTree(child, childX, childY, xOffset / childCount);
            }
        }
    }
    
    private void showNodeDetails(Node node) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Detalles del Nodo: " + node.value);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());
        
        JTextArea detailsArea = new JTextArea();
        detailsArea.setText("Nodo: " + node.value + "\n\n" +
                           "Hijos: " + node.children.size() + "\n" +
                           "Padre: " + (node.parent != null ? node.parent.value : "Ninguno"));
        detailsArea.setEditable(false);
        
        dialog.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    public void zoomIn() {
        scale *= 1.1;
        scale = Math.min(scale, 5.0);
        updateStatus();
        updateTreeUI();
    }
    
    public void zoomOut() {
        scale /= 1.1;
        scale = Math.max(scale, 0.1);
        updateStatus();
        updateTreeUI();
    }
    
    public void resetView() {
        scale = 1.0;
        offsetX = 0;
        offsetY = 0;
        updateStatus();
        updateTreeUI();
    }
    
    private void updateStatus() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JLabel statusLabel = (JLabel) ((BorderLayout) topFrame.getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        statusLabel.setText(String.format(" Zoom: %.0f%% | Haga clic en los nodos para ver detalles", scale * 100));
    }
}

class NaryTree {
    private Node root;
    
    public NaryTree(int rootValue) {
        root = new Node(rootValue);
    }
    
    public Node getRoot() {
        return root;
    }
    
    public void addChildToRoot(int value) {
        root.addChild(new Node(value));
    }
    
    public void addNodeAboveRoot(int value) {
        Node newRoot = new Node(value);
        newRoot.addChild(root);
        root = newRoot;
    }
    
    public void addNodeLeftToRoot(int value) {
        if (root.parent != null) {
            int index = root.parent.children.indexOf(root);
            root.parent.children.add(index, new Node(value));
        } else {
            addNodeAboveRoot(value);
        }
    }
    
    public void addNodeRightToRoot(int value) {
        if (root.parent != null) {
            int index = root.parent.children.indexOf(root);
            root.parent.children.add(index + 1, new Node(value));
        } else {
            Node newRoot = new Node(value);
            newRoot.addChild(root);
            root = newRoot;
        }
    }
}

class Node {
    int value;
    Node parent;
    List<Node> children;
    
    public Node(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
    
    public void addChild(Node child) {
        child.parent = this;
        children.add(child);
    }
}