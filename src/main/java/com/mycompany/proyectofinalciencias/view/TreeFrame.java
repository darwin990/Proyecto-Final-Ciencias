/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.view;

/**
 *
 * @author moral
 */

import com.mycompany.proyectofinalciencias.controller.TreeController;
import com.mycompany.proyectofinalciencias.model.NaryTree;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.*;

public class TreeFrame extends JFrame {
    private TreeController controller;
    private NaryTree tree;
    private TreePanel treePanel;
    private Random random = new Random();
    private JLabel statusLabel;
    
    public TreeFrame(TreeController controller) {
        this.controller = controller;
        initializeUI();
    }
    
    public TreeFrame() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Árbol N-ario con Nodos Botón");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear árbol con raíz inicial
        tree = this.controller.getModel(); // Valor inicial para la raíz

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

}
