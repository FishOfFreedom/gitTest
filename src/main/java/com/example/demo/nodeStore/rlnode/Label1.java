package com.example.demo.nodeStore.rlnode;

import javafx.scene.control.Label;

public class Label1 extends Label {
    public Label1(String text) {
        super(text);
    }

    public RLNode getRlNode() {
        return rlNode;
    }

    public void setRlNode(RLNode rlNode) {
        this.rlNode = rlNode;
    }

    private RLNode rlNode;
}
