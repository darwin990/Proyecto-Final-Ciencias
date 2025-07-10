package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class CorruptionTree {
    private CorruptionNode root;
    private Random random = new Random();

    public CorruptionTree(CorruptionNode root) {
        this.root = root;
    }

    public CorruptionNode getRoot() {
        return root;
    }

    public CorruptionNode findById(String id) {
        if (root == null) return null;
        Queue<CorruptionNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            CorruptionNode current = queue.poll();
            if (current.getId().equals(id)) return current;
            queue.addAll(current.getChildren());
        }
        return null;
    }

    public boolean insert(String parentId, CorruptionNode newNode) {
        CorruptionNode parent = findById(parentId);
        if (parent != null) {
            parent.addChild(newNode);
            return true;
        }
        return false;
    }

    public boolean remove(String nodeId) {
        CorruptionNode node = findById(nodeId);
        if (node == null || node == root) return false;
        CorruptionNode parent = node.getParent();
        return parent.getChildren().remove(node);
    }

    public void traverseDFS(CorruptionNode node, List<CorruptionNode> visited) {
        if (node == null) return;
        visited.add(node);
        for (CorruptionNode child : node.getChildren()) {
            traverseDFS(child, visited);
        }
    }

    public List<CorruptionNode> traverseBFS() {
        List<CorruptionNode> visited = new ArrayList<>();
        if (root == null) return visited;

        Queue<CorruptionNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            CorruptionNode current = queue.poll();
            visited.add(current);
            queue.addAll(current.getChildren());
        }
        return visited;
    }

    public double calcularInfluenciaTotal() {
        double total = 0;
        for (CorruptionNode node : traverseBFS()) {
            total += node.getInfluence();
        }
        return total;
    }

    public double calcularRiquezaTotal() {
        double total = 0;
        for (CorruptionNode node : traverseBFS()) {
            total += node.getWealth();
        }
        return total;
    }

    public double calcularRiesgoTotal() {
        double total = 0;
        for (CorruptionNode node : traverseBFS()) {
            total += node.getExposureRisk();
        }
        return total;
    }

    public void ejecutarTurno() {
        for (CorruptionNode node : traverseBFS()) {
            if (node.getState().equals("activo")) {
                node.setWealth(node.getWealth() + node.getWealth() * 0.1);
                node.setInfluence(node.getInfluence() + node.getInfluence() * 0.05);

                int traicion = random.nextInt(100);
                if (node.getAmbition() > 70 && node.getLoyalty() < 40 && traicion > node.getLoyalty()) {
                    node.setState("traidor");
                }

                int exp = random.nextInt(100);
                if (node.getExposureRisk() > exp) {
                    node.setState("bajo sospecha");
                }
            }
        }
    }
} 
