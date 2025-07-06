/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalciencias.model;

import java.util.ArrayList;
import java.util.List;

public class NaryTree {
    private Node root;
    
    public NaryTree(int rootValue) {
        root = new Node(rootValue);
    }
    
    public Node getRoot() {
        return root;
    }
    
    public void addChildToRoot(int value) {
        root.addChild(new Node(value));
    }
    
    public void addNodeAboveRoot(int value) {
        Node newRoot = new Node(value);
        newRoot.addChild(root);
        root = newRoot;
    }
    
    public void addNodeLeftToRoot(int value) {
        if (root.getParent() != null) {
            int index = root.getParent().getChildren().indexOf(root);
            root.getParent().getChildren().add(index, new Node(value));
        } else {
            addNodeAboveRoot(value);
        }
    }
    
    public void addNodeRightToRoot(int value) {
        if (root.getParent() != null) {
            int index = root.getParent().getChildren().indexOf(root);
            root.getParent().getChildren().add(index + 1, new Node(value));
        } else {
            Node newRoot = new Node(value);
            newRoot.addChild(root);
            root = newRoot;
        }
    }
}