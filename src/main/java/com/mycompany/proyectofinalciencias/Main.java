/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias;

/**
 *
 * @author moral
 */

import com.mycompany.proyectofinalciencias.controller.GraphController;
import com.mycompany.proyectofinalciencias.model.Graph;
import com.mycompany.proyectofinalciencias.controller.TreeController;
import com.mycompany.proyectofinalciencias.model.NaryTree;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Iniciar visualizador de árbol
        SwingUtilities.invokeLater(() -> {
            NaryTree tree = new NaryTree(50);
            TreeController treeController = new TreeController(tree);
        });
        
        // Iniciar visualizador de grafo
        SwingUtilities.invokeLater(() -> {
            Map<String, List<String>> sampleGraph = new HashMap<>();
            sampleGraph.put("A", Arrays.asList("B", "C"));
            sampleGraph.put("B", Arrays.asList("A", "D", "E"));
            // Resto del grafo...
            
            Graph graph = new Graph(sampleGraph);
            GraphController graphController = new GraphController(graph);
        });
    }
}