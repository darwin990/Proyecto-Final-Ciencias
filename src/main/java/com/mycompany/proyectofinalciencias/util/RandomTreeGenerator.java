// src/main/java/com/mycompany/proyectofinalciencias/util/RandomTreeGenerator.java
package com.mycompany.proyectofinalciencias.util;

import com.mycompany.proyectofinalciencias.model.CorruptionNode;
import com.mycompany.proyectofinalciencias.model.CorruptionTree;

import java.util.Random;
import java.util.UUID;

public class RandomTreeGenerator {

    private static final Random random = new Random();

    public static CorruptionTree generar() {
        CorruptionNode root = new CorruptionNode("R1", "Líder Supremo", "Presidente");
        root.setWealth(200);
        root.setInfluence(150);
        root.setLoyalty(90);
        root.setAmbition(40);
        root.setExposureRisk(10);
        root.setState("activo");
        root.setAcceptsBribes(false);

        CorruptionTree tree = new CorruptionTree(root);

        for (int i = 1; i <= 3; i++) {
            CorruptionNode child = crearNodoEjemplo("M" + i, "Ministro " + i);
            tree.insert(root.getId(), child);

            for (int j = 1; j <= 2 + random.nextInt(2); j++) {
                CorruptionNode sub = crearNodoEjemplo("F" + i + j, "Funcionario " + i + "." + j);
                tree.insert(child.getId(), sub);
            }
        }

        return tree;
    }

    private static CorruptionNode crearNodoEjemplo(String id, String name) {
        CorruptionNode node = new CorruptionNode(id, name, "Funcionario");

        node.setBribeCost(40 + random.nextInt(60));
        node.setInfluence(10 + random.nextDouble() * 30);
        node.setWealth(20 + random.nextDouble() * 50);
        node.setLoyalty(30 + random.nextInt(60));
        node.setAmbition(20 + random.nextInt(60));
        node.setExposureRisk(10 + random.nextInt(50));

        node.setState(random.nextDouble() < 0.5 ? "inactivo" : "activo");
        node.setAcceptsBribes(true);

        return node;
    }
}
