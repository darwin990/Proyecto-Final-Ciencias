/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.controller;

/**
 *
 * @author moral
 */

import com.mycompany.proyectofinalciencias.model.Graph;
import com.mycompany.proyectofinalciencias.view.GraphFrame;
import com.mycompany.proyectofinalciencias.view.GraphPanel;

public class GraphController {
    private Graph model;
    private GraphFrame view;

    public GraphController(Graph model) {
        this.model = model;
        this.view = new GraphFrame(this);
        this.view.setVisible(true);
    }

    public void refreshGraph() {
        view.getGraphPanel().refreshGraph();
    }

    public Graph getModel() {
        return model;
    }

    public void setModel(Graph model) {
        this.model = model;
    }

    public GraphFrame getView() {
        return view;
    }

    public void setView(GraphFrame view) {
        this.view = view;
    }
    
    
}
