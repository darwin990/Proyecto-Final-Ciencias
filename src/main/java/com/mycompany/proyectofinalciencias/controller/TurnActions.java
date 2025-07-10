package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

public class TurnActions {

    public static void recolectarRecursos(CorruptionTree tree, PlayerStatus jugador) {
        double totalDinero = 0;
        double totalInfluencia = 0;

        for (CorruptionNode nodo : tree.traverseBFS()) {
            if ("activo".equalsIgnoreCase(nodo.getState())) {
                totalDinero += nodo.getWealth() * 0.1;
                totalInfluencia += nodo.getInfluence() * 0.1;
            }
        }

        jugador.aumentarDinero(totalDinero);
        jugador.aumentarInfluencia(totalInfluencia);
    }

    public static boolean expandirRed(CorruptionTree tree, PlayerStatus jugador,
                                      String idPadre, CorruptionNode nuevo) {
        CorruptionNode padre = tree.findById(idPadre);
        if (padre == null || jugador.getDinero() < nuevo.getBribeCost()) return false;

        boolean insertado = tree.insert(idPadre, nuevo);
        if (insertado) {
            jugador.restarDinero(nuevo.getBribeCost());
            nuevo.setLoyalty((int) (20 + Math.random() * 60));
            nuevo.setAmbition((int) (10 + Math.random() * 50));
            nuevo.setExposureRisk((int) (10 + Math.random() * 50));
            return true;
        }
        return false;
    }

    public static boolean mejorarLealtad(CorruptionTree tree, String idNodo, double inversion) {
        CorruptionNode nodo = tree.findById(idNodo);
        if (nodo != null && "activo".equalsIgnoreCase(nodo.getState())) {
            int actual = nodo.getLoyalty();
            int incremento = (int) (inversion * 0.5);
            int nuevoNivel = Math.min(100, actual + incremento);
            nodo.setLoyalty(nuevoNivel);
            return true;
        }
        return false;
    }

    public static boolean cortarConexion(ExternalGraph grafo, CorruptionTree tree,
                                         PlayerStatus jugador, String origen, String destino, double costoInflu) {
        return jugador.romperConexion(grafo, tree, origen, destino, costoInflu);
    }

    public static boolean ejecutarSabotaje(ExternalGraph grafo, PlayerStatus jugador,
                                           String objetivoId, double penalidadReputacion) {
        return jugador.realizarSabotaje(grafo, objetivoId, 0, penalidadReputacion);
    }
}
