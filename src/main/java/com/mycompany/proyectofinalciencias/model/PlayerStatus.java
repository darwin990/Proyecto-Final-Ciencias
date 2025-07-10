package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class PlayerStatus {
    private double sospechaGlobal;
    private double reputacionPublica;
    private double dinero;
    private double influencia;

    public PlayerStatus() {
        this.sospechaGlobal = 0;
        this.reputacionPublica = 100;
        this.dinero = 0;
        this.influencia = 0;
    }

    public void disminuirReputacion(double cantidad) {
        reputacionPublica = Math.max(0, reputacionPublica - cantidad);
    }

    public void aumentarSospecha(double cantidad) {
        sospechaGlobal = Math.min(100, sospechaGlobal + cantidad);
    }

    public void reducirSospecha(double cantidad) {
        sospechaGlobal = Math.max(0, sospechaGlobal - cantidad);
    }

    public void aumentarReputacion(double cantidad) {
        reputacionPublica = Math.min(100, reputacionPublica + cantidad);
    }

    public void reducirReputacion(double cantidad) {
        reputacionPublica = Math.max(0, reputacionPublica - cantidad);
    }

    public double getSospechaGlobal() {
        return sospechaGlobal;
    }

    public double getReputacionPublica() {
        return reputacionPublica;
    }

    public boolean estaPerdido() {
        return sospechaGlobal >= 100;
    }

    public boolean realizarEncubrimiento(CorruptionTree tree, double costo) {
        for (CorruptionNode node : tree.traverseBFS()) {
            if (node.getState().equals("activo") && node.getWealth() >= costo) {
                node.setWealth(node.getWealth() - costo);
                reducirSospecha(costo * 0.2);
                System.out.println("[ENCUBRIMIENTO] " + node.getName() + " gastó " + costo + " para reducir sospecha.");
                return true;
            }
        }
        System.out.println("[ENCUBRIMIENTO FALLIDO] No hay nodos activos con fondos suficientes.");
        return false;
    }

    public boolean realizarSabotaje(ExternalGraph grafo, String nodeId, double costo, double penalidadReputacion) {
        if (getReputacionPublica() < penalidadReputacion) return false;

        boolean sabotaje = grafo.sabotearNodo(nodeId);
        if (sabotaje) {
            disminuirReputacion(penalidadReputacion);
            return true;
        }
        return false;
    }

    public boolean romperConexion(ExternalGraph grafo, CorruptionTree tree, String a, String b, double costoInfluence) {
        for (CorruptionNode nodo : tree.traverseBFS()) {
            if (nodo.getState().equalsIgnoreCase("activo") && nodo.getInfluence() >= costoInfluence) {
                if (grafo.eliminarConexion(a, b)) {
                    nodo.setInfluence(nodo.getInfluence() - costoInfluence);
                    return true;
                }
            }
        }
        return false;
    }

    public void aumentarDinero(double monto) {
        this.dinero += monto;
    }

    public void aumentarInfluencia(double monto) {
        this.influencia += monto;
    }

    public void restarDinero(double monto) {
        this.dinero = Math.max(0, this.dinero - monto);
    }

    public double getDinero() {
        return this.dinero;
    }

    public double getInfluencia() {
        return this.influencia;
    }
}
