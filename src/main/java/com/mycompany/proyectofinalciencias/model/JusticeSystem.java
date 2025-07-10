package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class JusticeSystem {
    private final Map<String, Double> evidenceByNode;
    private final List<Investigation> activeInvestigations;
    private final GameSession session;
    private final Random random;

    public JusticeSystem(GameSession session) {
        this.session = session;
        this.evidenceByNode = new HashMap<>();
        this.activeInvestigations = new ArrayList<>();
        this.random = new Random();
    }

    public void update() {
        // Procesar investigaciones activas
        Iterator<Investigation> it = activeInvestigations.iterator();
        while (it.hasNext()) {
            Investigation inv = it.next();
            if (inv.advance()) {
                concludeInvestigation(inv);
                it.remove();
            }
        }

        // Posibilidad de iniciar nuevas investigaciones
        if (random.nextDouble() < calculateNewInvestigationChance()) {
            startRandomInvestigation();
        }
    }

    private double calculateNewInvestigationChance() {
        double baseChance = 0.2; // 20% base
        double suspicionMultiplier = session.getJugador().getSospechaGlobal() / 100.0;
        return baseChance * suspicionMultiplier;
    }

    private void startRandomInvestigation() {
        List<CorruptionNode> activeNodes = session.getArbol().traverseBFS().stream()
            .filter(n -> n.getState().equals("activo"))
            .toList();

        if (!activeNodes.isEmpty()) {
            CorruptionNode target = activeNodes.get(random.nextInt(activeNodes.size()));
            startInvestigation(target.getId());
        }
    }

    public void startInvestigation(String nodeId) {
        if (isUnderInvestigation(nodeId)) return;

        Investigation inv = new Investigation(nodeId, 3 + random.nextInt(3)); // 3-5 turnos
        activeInvestigations.add(inv);
        
        session.notifyEvent(new GameEvent(
            "JUSTICIA",
            "🔍 Se ha iniciado una investigación sobre " + 
            session.getArbol().findById(nodeId).getName(),
            GameEvent.EventSeverity.WARNING
        ));
    }

    private void concludeInvestigation(Investigation inv) {
        CorruptionNode node = session.getArbol().findById(inv.getTargetId());
        if (node == null) return;

        double evidence = inv.getEvidence();
        evidenceByNode.merge(inv.getTargetId(), evidence, Double::sum);

        if (evidence >= 100) {
            node.setState("investigado");
            session.getJugador().aumentarSospecha(20);
            session.notifyEvent(new GameEvent(
                "JUSTICIA",
                "⚖️ " + node.getName() + " ha sido investigado exitosamente",
                GameEvent.EventSeverity.CRITICAL
            ));
        } else {
            node.setState("bajo sospecha");
            session.getJugador().aumentarSospecha(5);
            session.notifyEvent(new GameEvent(
                "JUSTICIA",
                "👁️ " + node.getName() + " está bajo sospecha",
                GameEvent.EventSeverity.WARNING
            ));
        }
    }

    public boolean isUnderInvestigation(String nodeId) {
        return activeInvestigations.stream()
            .anyMatch(inv -> inv.getTargetId().equals(nodeId));
    }

    public List<Investigation> getActiveInvestigations() {
        return Collections.unmodifiableList(activeInvestigations);
    }

    public double getEvidence(String nodeId) {
        return evidenceByNode.getOrDefault(nodeId, 0.0);
    }
}