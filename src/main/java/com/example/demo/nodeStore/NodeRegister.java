package com.example.demo.nodeStore;

import com.example.demo.nodeStore.rlnode.RLAnchorPane;
import com.example.demo.nodeStore.rlnode.RLBorderPane;
import com.example.demo.nodeStore.rlnode.RLNode;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.Map;

public class NodeRegister {
    public static Map<RLNode,String> map;

    public static void register(RLNode rlNode,String s){
        map.put(rlNode,s);
    }

    public static RLNode getNode(String type) {
        switch (type) {
            case "RLAnchorPane":
                return new RLAnchorPane("RLAnchorPane");
            case "RLBorderPane":
                return new RLBorderPane("RLBorderPane");
            case "ROOT":
                return new RLNode("root");
            default:
                throw new IllegalArgumentException("Unknown node type: " + type);
        }
    }
}
