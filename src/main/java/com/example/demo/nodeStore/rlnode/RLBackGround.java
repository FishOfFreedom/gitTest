package com.example.demo.nodeStore.rlnode;

import javafx.scene.image.Image;

public class RLBackGround {
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public RLBackGround(Image image) {
        this.image = image;
    }

    private Image image;
}
