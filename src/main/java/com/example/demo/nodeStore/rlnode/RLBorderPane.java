package com.example.demo.nodeStore.rlnode;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RLBorderPane extends RLPane{
    public RLBorderPane(String name) {
        super(name);
    }

    @Override
    public void renderBackGround(GraphicsContext gc) {
        RLBackGround rlBackGround = getRlBackGround();
        int layoutX = getLayoutX();
        int layoutY = getLayoutY();
        int width = getWidth();
        int height = getHeight();
        if(rlBackGround!=null){
            gc.drawImage(rlBackGround.getImage(),layoutX,layoutY,width,height);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        int layoutX = getLayoutX();
        int layoutY = getLayoutY();
        int width = getWidth();
        int height = getHeight();
    }
}
