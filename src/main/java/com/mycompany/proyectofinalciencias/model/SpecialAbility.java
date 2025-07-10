package com.mycompany.proyectofinalciencias.model;

public enum SpecialAbility {
    BLOCK_INVESTIGATION("Bloquear Investigación", "Reduce la probabilidad de investigación en un área"),
    VOTE_BUYING("Compra de Votos", "Aumenta la influencia política temporalmente"),
    RIGGED_CONTRACTS("Contratación Amañada", "Genera riqueza adicional cada turno"),
    MEDIA_MANIPULATION("Manipulación Mediática", "Reduce el impacto de escándalos"),
    HITMAN_CONTRACTS("Contrato de Sicarios", "Elimina nodos problemáticos");

    private final String nombre;
    private final String descripcion;

    SpecialAbility(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}