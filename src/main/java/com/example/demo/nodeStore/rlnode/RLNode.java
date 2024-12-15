package com.example.demo.nodeStore.rlnode;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class RLNode {
    private String name;
    private String displayName;
    private int layoutX;
    private int layoutY;
    private int width;
    private int height;
    private boolean isLocked;
    private RLBackGround rlBackGround;

    public String getDisplayName() {
        return displayName;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public RLBackGround getRlBackGround() {
        return rlBackGround;
    }

    public void outPos(){
        System.out.println(getDisplayName()+":"+getLayoutX()+" "+getLayoutY()+" "+getWidth()+" "+getHeight());
    }

    public void setRlBackGround(RLBackGround rlBackGround) {
        this.rlBackGround = rlBackGround;
    }

    public int getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(int layoutX) {
        this.layoutX = layoutX;
    }

    public int getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RLNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void render(GraphicsContext gc) {
        renderBackGround(gc);
    }

    public void renderBackGround(GraphicsContext gc) {
    }

    public void update() {
    }

    public RLNode getIsInMouse(float mouseX,float mouseY) {
        if(mouseX>layoutX&&mouseX<layoutX+width&&mouseY>layoutY&&mouseY<layoutY+height)
            return this;
        return null;
    }
}
