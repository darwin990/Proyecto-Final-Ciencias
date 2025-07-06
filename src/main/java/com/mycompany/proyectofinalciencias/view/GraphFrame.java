/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.view;

/**
 *
 * @author moral
 */

import com.mycompany.proyectofinalciencias.controller.GraphController;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class GraphFrame extends JFrame {
    private GraphController controller;
    private Map<String, List<String>> graph;
    private GraphPanel graphPanel;

    public GraphFrame(GraphController controller) {
        this.graph = controller.getModel().getAdjacencyList();
        this.controller = controller;
        initializeUI();
    }
    
    public GraphFrame(){
        initializeUI();
    }

    private void initializeUI() {
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
    

    public GraphPanel getGraphPanel() {
        return graphPanel; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}