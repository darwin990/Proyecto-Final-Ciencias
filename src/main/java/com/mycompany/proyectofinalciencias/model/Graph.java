/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author moral
 */
package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public Graph(Map<String, List<String>> initialGraph) {
        this.adjacencyList = new HashMap<>(initialGraph);
    }

    public void addNode(String node, List<String> connections) {
        adjacencyList.put(node, connections);
    }

    public Map<String, List<String>> getAdjacencyList() {
        return Collections.unmodifiableMap(adjacencyList);
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public List<String> getConnections(String node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }
    
    
}