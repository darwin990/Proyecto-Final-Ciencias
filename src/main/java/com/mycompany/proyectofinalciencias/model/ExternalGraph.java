package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class ExternalGraph {
    private Map<String, ExternalNode> nodes;
    private Map<String, List<Connection>> adjacencyList;

    public ExternalGraph() {
        nodes = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addNode(String id, String label, String type) {
        ExternalNode node = new ExternalNode(id, label, type);
        nodes.put(id, node);
        adjacencyList.put(id, new ArrayList<>());
    }

    public void addConnection(String fromId, String toId, double weight) {
        if (nodes.containsKey(fromId) && nodes.containsKey(toId)) {
            adjacencyList.get(fromId).add(new Connection(toId, weight));
            adjacencyList.get(toId).add(new Connection(fromId, weight)); // bidirectional
        }
    }

    public List<Connection> getConnections(String id) {
        return adjacencyList.getOrDefault(id, new ArrayList<>());
    }

    public Map<String, ExternalNode> getNodes() {
        return nodes;
    }

    public List<String> dijkstraShortestPath(String startId, String endId) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<Connection> queue = new PriorityQueue<>(Comparator.comparingDouble(Connection::getWeight));

        for (String id : nodes.keySet()) {
            dist.put(id, Double.MAX_VALUE);
            prev.put(id, null);
        }
        dist.put(startId, 0.0);
        queue.add(new Connection(startId, 0.0));

        while (!queue.isEmpty()) {
            Connection current = queue.poll();
            String currentId = current.getTarget();

            for (Connection neighbor : adjacencyList.getOrDefault(currentId, List.of())) {
                double alt = dist.get(currentId) + neighbor.getWeight();
                if (alt < dist.get(neighbor.getTarget())) {
                    dist.put(neighbor.getTarget(), alt);
                    prev.put(neighbor.getTarget(), currentId);
                    queue.add(new Connection(neighbor.getTarget(), alt));
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        for (String at = endId; at != null; at = prev.get(at)) {
            path.addFirst(at);
        }
        return path;
    }

    public static class ExternalNode {
        private String id;
        private String label;
        private String type;

        public ExternalNode(String id, String label, String type) {
            this.id = id;
            this.label = label;
            this.type = type;
        }

        public String getId() { return id; }
        public String getLabel() { return label; }
        public String getType() { return type; }
    }

    public static class Connection {
        private String target;
        private double weight;

        public Connection(String target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        public String getTarget() { return target; }
        public double getWeight() { return weight; }
    }

    public boolean eliminarConexion(String a, String b){
        if (!adjacencyList.containsKey(a) || !adjacencyList.containsKey(b)) return false;

        List<Connection> listaA = adjacencyList.get(a);
        List<Connection> listaB = adjacencyList.get(b);

        boolean removedA = listaA.removeIf(c -> c.getTarget().equals(b));
        boolean removedB = listaB.removeIf(c -> c.getTarget().equals(a));

        return removedA || removedB;


    }

    public boolean sabotearNodo(String nodeId) {
        if (!nodes.containsKey(nodeId)) return false;

        // Borrar conexiones entrantes
        for (String other : adjacencyList.keySet()) {
            adjacencyList.get(other).removeIf(c -> c.getTarget().equals(nodeId));
        }

        // Borrar conexiones salientes
        adjacencyList.get(nodeId).clear();

        // Marcar nodo como saboteado
        ExternalNode nodo = nodes.get(nodeId);
        nodo.type = "saboteado"; // afecta color en visualización si quieres

        return true;
    }
        public void agregarConexion(String fromId, String toId, int peso) {
            addConnection(fromId, toId, (double) peso);
        }

}
