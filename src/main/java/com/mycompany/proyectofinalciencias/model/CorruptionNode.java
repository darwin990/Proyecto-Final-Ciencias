package com.mycompany.proyectofinalciencias.model;

import java.util.ArrayList;
import java.util.List;

public class CorruptionNode {
    private String id;
    private String name;
    private String role;
    private double bribeCost;
    private double influence;
    private double wealth;
    private int loyalty;
    private int ambition;
    private int exposureRisk;
    private SpecialAbility specialAbility; // Cambiar String por SpecialAbility
    private String state;
    private boolean acceptsBribes;
    private int abilityCooldown = 0;

    private CorruptionNode parent;
    private List<CorruptionNode> children;

    public CorruptionNode(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.children = new ArrayList<>();
        this.acceptsBribes = true;
        this.state = "activo";
        this.specialAbility = asignarHabilidadPorRol(role); // Asignar habilidad según rol
    }

    private SpecialAbility asignarHabilidadPorRol(String role) {
        return switch (role.toLowerCase()) {
            case "alcalde" -> SpecialAbility.VOTE_BUYING;
            case "contratista" -> SpecialAbility.RIGGED_CONTRACTS;
            case "juez" -> SpecialAbility.BLOCK_INVESTIGATION;
            case "periodista" -> SpecialAbility.MEDIA_MANIPULATION;
            case "militar" -> SpecialAbility.HITMAN_CONTRACTS;
            default -> null;
        };
    }

    public void addChild(CorruptionNode child) {
        child.setParent(this);
        children.add(child);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getBribeCost() { return bribeCost; }
    public void setBribeCost(double bribeCost) { this.bribeCost = bribeCost; }

    public double getInfluence() { return influence; }
    public void setInfluence(double influence) { this.influence = influence; }

    public double getWealth() { return wealth; }
    public void setWealth(double wealth) { this.wealth = wealth; }

    public int getLoyalty() { return loyalty; }
    public void setLoyalty(int loyalty) { this.loyalty = loyalty; }

    public int getAmbition() { return ambition; }
    public void setAmbition(int ambition) { this.ambition = ambition; }

    public int getExposureRisk() { return exposureRisk; }
    public void setExposureRisk(int exposureRisk) { this.exposureRisk = exposureRisk; }

    public SpecialAbility getSpecialAbility() { return specialAbility; }
    public void setSpecialAbility(SpecialAbility ability) { this.specialAbility = ability; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public boolean isAcceptsBribes() { return acceptsBribes; }
    public void setAcceptsBribes(boolean acceptsBribes) { this.acceptsBribes = acceptsBribes; }

    public CorruptionNode getParent() { return parent; }
    public void setParent(CorruptionNode parent) { this.parent = parent; }

    public List<CorruptionNode> getChildren() { return children; }
    public void setChildren(List<CorruptionNode> children) { this.children = children; }

    public void activarHabilidad(GameSession session) {
        if (specialAbility == null || !state.equals("activo") || abilityCooldown > 0) {
            return;
        }

        switch (specialAbility) {
            case BLOCK_INVESTIGATION -> {
                session.getJugador().reducirSospecha(5);
                session.notifyEvent(new GameEvent(
                    "HABILIDAD",
                    "🛡️ " + name + " bloqueó parte de la investigación",
                    GameEvent.EventSeverity.INFO
                ));
            }
            case VOTE_BUYING -> {
                influence += 20;
                session.notifyEvent(new GameEvent(
                    "HABILIDAD",
                    "🗳️ " + name + " compró votos para aumentar influencia",
                    GameEvent.EventSeverity.INFO
                ));
            }
            case RIGGED_CONTRACTS -> {
                wealth += 50;
                session.notifyEvent(new GameEvent(
                    "HABILIDAD",
                    "📑 " + name + " generó contratos amañados",
                    GameEvent.EventSeverity.INFO
                ));
            }
            case MEDIA_MANIPULATION -> {
                session.getJugador().aumentarReputacion(10);
                session.notifyEvent(new GameEvent(
                    "HABILIDAD",
                    "📰 " + name + " manipuló la opinión pública",
                    GameEvent.EventSeverity.INFO
                ));
            }
            case HITMAN_CONTRACTS -> {
                // Esta habilidad se implementará cuando se agregue la opción de eliminar nodos
                session.notifyEvent(new GameEvent(
                    "HABILIDAD",
                    "🎯 " + name + " tiene sicarios disponibles",
                    GameEvent.EventSeverity.WARNING
                ));
            }
        }

        abilityCooldown = 3; // La habilidad no se podrá usar por 3 turnos
    }

    public void reduceCooldown() {
        if (abilityCooldown > 0) {
            abilityCooldown--;
        }
    }
}