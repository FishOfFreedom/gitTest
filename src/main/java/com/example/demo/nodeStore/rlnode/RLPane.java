package com.example.demo.nodeStore.rlnode;

import java.util.ArrayList;
import java.util.List;

public class RLPane extends RLNode{
    public RLPane(String name) {
        super(name);
    }


    public List<RLNode> getChild() {
        return child;
    }

    private List<RLNode> child = new ArrayList<>();

    @Override
    public void update() {
        List<RLNode> child1 = getChild();
        for(RLNode rlNode:child1){
            rlNode.update();
        }
    }

    @Override
    public void outPos() {
        super.outPos();
        List<RLNode> child1 = getChild();
        for(RLNode rlNode:child1){
            rlNode.outPos();
        }
    }

    @Override
    public RLNode getIsInMouse(float mouseX, float mouseY) {
        List<RLNode> child1 = getChild();
        for(RLNode rlNode:child1){
            RLNode isInMouse = rlNode.getIsInMouse(mouseX, mouseY);
            if(isInMouse!=null)
                return isInMouse;
        }
        return super.getIsInMouse(mouseX, mouseY);
    }
}
