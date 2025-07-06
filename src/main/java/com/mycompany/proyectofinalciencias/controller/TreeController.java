/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.controller;

/**
 *
 * @author moral
 */

import com.mycompany.proyectofinalciencias.model.NaryTree;
import com.mycompany.proyectofinalciencias.view.TreeFrame;

public class TreeController {
    private NaryTree model;
    private TreeFrame view;
    
    public TreeController(NaryTree model) {
        this.model = model;
        this.view = new TreeFrame(this);
        this.view.setVisible(true);
    }

    public NaryTree getModel() {
        return model;
    }

    public void setModel(NaryTree model) {
        this.model = model;
    }

    public TreeFrame getView() {
        return view;
    }

    public void setView(TreeFrame view) {
        this.view = view;
    }
    
    
}