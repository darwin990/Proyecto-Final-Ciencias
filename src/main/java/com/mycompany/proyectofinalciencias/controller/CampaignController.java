package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

public class CampaignController {
    private final Campaign campaña;

    public CampaignController(Campaign campaña) {
        this.campaña = campaña;
    }

    public boolean verificarYAvanzar(CorruptionTree arbol, PlayerStatus jugador) {
        if (campaña.estaCompleta()) {
            System.out.println("✅ ¡Felicidades! Has completado la campaña.");
            return true; // detener juego
        }

        if (campaña.avanzarSiCumple(arbol, jugador)) {
            System.out.println("🎯 Nivel superado: " + campaña.nombreNivelActual());
            System.out.println("🚀 Nuevo objetivo activado.");
        }

        return false;
    }

    public String getNivelActualNombre() {
        return campaña.nombreNivelActual();
    }

    public boolean estaCompleta() {
        return campaña.estaCompleta();
    }

    public Campaign getCampaign() {
        return campaña;
    }
}
