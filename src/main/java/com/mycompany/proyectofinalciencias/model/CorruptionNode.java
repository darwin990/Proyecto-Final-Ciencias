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
    private String specialAbility;
    private String state;
    private boolean acceptsBribes;

    private CorruptionNode parent;
    private List<CorruptionNode> children;

    public CorruptionNode(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.children = new ArrayList<>();
        this.acceptsBribes = true;
        this.state = "activo";
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

    public String getSpecialAbility() { return specialAbility; }
    public void setSpecialAbility(String specialAbility) { this.specialAbility = specialAbility; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public boolean isAcceptsBribes() { return acceptsBribes; }
    public void setAcceptsBribes(boolean acceptsBribes) { this.acceptsBribes = acceptsBribes; }

    public CorruptionNode getParent() { return parent; }
    public void setParent(CorruptionNode parent) { this.parent = parent; }

    public List<CorruptionNode> getChildren() { return children; }
    public void setChildren(List<CorruptionNode> children) { this.children = children; }
} 