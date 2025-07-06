/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.model;

/**
 *
 * @author moral
 */

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int value;
    private Node parent;
    private List<Node> children;
    
    public Node(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
    
    public void addChild(Node child) {
        child.parent = this;
        children.add(child);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
    
}