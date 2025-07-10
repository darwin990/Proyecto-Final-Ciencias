package com.mycompany.proyectofinalciencias.model;

import java.util.*;

public class Campaign {
    private List<CampaignLevel> niveles;
    private int nivelActual;

    public Campaign() {
        niveles = new ArrayList<>();
        nivelActual = 0;

        // Definir niveles
        niveles.add(new CampaignLevel("Concejo Municipal", 2, 30));
        niveles.add(new CampaignLevel("Gobernación", 3, 40));
        niveles.add(new CampaignLevel("Congreso", 4, 50));
        niveles.add(new CampaignLevel("Presidencia", 5, 60));
    }

    public Campaign(List<CampaignLevel> niveles) {
        this.niveles = niveles;
        this.nivelActual = 0;
    }

    public CampaignLevel getNivelActual() {
        return niveles.get(nivelActual);
    }

    public boolean avanzarSiCumple(CorruptionTree tree, PlayerStatus jugador) {
        CampaignLevel actual = getNivelActual();
        if (!actual.isCompletado() && actual.verificar(tree, jugador)) {
            actual.marcarCompletado();
            nivelActual++;
            return true;
        }
        return false;
    }

    public boolean estaCompleta() {
        return nivelActual >= niveles.size();
    }

    public String nombreNivelActual() {
        return estaCompleta() ? "¡Campaña finalizada!" : getNivelActual().getNombre();
    }
    public String objetivoActual() {
    if (nivelActual < niveles.size()) {
        return niveles.get(nivelActual).getDescripcionObjetivo();
    }
    return "🎉 Campaña completada.";
}

}
