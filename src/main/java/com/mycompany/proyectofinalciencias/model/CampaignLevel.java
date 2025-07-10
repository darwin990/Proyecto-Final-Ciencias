package com.mycompany.proyectofinalciencias.model;

public class CampaignLevel {
    private String nombre;
    private int objetivoNodosActivos;
    private double maxSospecha;
    private boolean completado;

    public CampaignLevel(String nombre, int objetivoNodosActivos, double maxSospecha) {
        this.nombre = nombre;
        this.objetivoNodosActivos = objetivoNodosActivos;
        this.maxSospecha = maxSospecha;
        this.completado = false;
    }

    public boolean verificar(CorruptionTree tree, PlayerStatus jugador) {
        int activos = (int) tree.traverseBFS().stream()
                .filter(n -> n.getState().equalsIgnoreCase("activo")).count();

        return activos >= objetivoNodosActivos && jugador.getSospechaGlobal() <= maxSospecha;
    }

    public String getNombre() { return nombre; }

    public boolean isCompletado() { return completado; }

    public void marcarCompletado() { this.completado = true; }

    public String getDescripcionObjetivo() {
        return "Mantén al menos " + objetivoNodosActivos +
               " nodos activos con sospecha global ≤ " + maxSospecha;
    }
}
