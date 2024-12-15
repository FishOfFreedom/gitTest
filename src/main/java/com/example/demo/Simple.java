package com.example.demo;

import com.example.demo.nodeStore.NodeRegister;
import com.example.demo.nodeStore.rlnode.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Simple extends Application {
    RLNode mousePressNode;
    RLNode mousePressNode1;
    Label1 label1;
    AnchorPane flowPane;
    RLPane gui;
    Stage stage;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(actionEvent -> {
            List<RLNode> child = gui.getChild();
            for(RLNode rlNode:child){
                rlNode.outPos();
            }
        });

        fileMenu.getItems().addAll(saveMenuItem);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1020,800);

        Canvas canvas = new Canvas(800,800);
        canvas.setOnMouseClicked(mouseEvent -> {
            List<RLNode> child = gui.getChild();
            for(RLNode rlNode:child){
                mousePressNode1 = rlNode.getIsInMouse((float) mouseEvent.getX(),(float)mouseEvent.getY());
                if(mousePressNode1!=null) {
                    if(!flowPane.getChildren().isEmpty())
                        flowPane.getChildren().remove(0);
                    flowPane.getChildren().add(addAttributeSetting(mousePressNode1));
                }
            }
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            if(mousePressNode1!=null){
                mousePressNode1.setLayoutX((int)mouseEvent.getX());
                mousePressNode1.setLayoutY((int)mouseEvent.getY());
            }
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            mousePressNode1 = null;
        });
        GraphicsContext graphics = canvas.getGraphicsContext2D();

        Accordion accordion = addNode();
        TreeView<Label1> treeView = addTree();
        VBox vBox = new VBox(accordion,treeView);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                graphics.clearRect(0,0,800,800);
                updateShape();
                drawShapes(graphics,treeView.getRoot());
            }
        };
        /*
        ar1:32 83 44 23
ar2:81 83 44 23
reloding:137 56 172 21
ar:133 80 124 27
buy:452 116 50 20
gun:506 117 240 18
         */

        flowPane = new AnchorPane();
        flowPane.getChildren().add(addAttributeSetting(new RLBorderPane("RLBorderPane")));
        flowPane.setStyle("-fx-border-color: #0ff");

        borderPane.setRight(flowPane);
        borderPane.setCenter(canvas);
        borderPane.setLeft(vBox);

        menuBar.getMenus().add(fileMenu);
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane, null);
        this.stage =stage;
        stage.setScene(scene);
        stage.setTitle("Gui Builder");
        stage.show();

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Accordion addNode(){
        //库
        ListView<Label1> listView = new ListView<>();

        Accordion accordion = new Accordion();
        Label1 label = addCanDragLabel("RLBorderPane");
        Label1 label1 = addCanDragLabel("RLAnchorPane");

        ObservableList<Label1> items = FXCollections.observableArrayList(label,label1);

        listView.setItems(items);
        TitledPane titledPane = new TitledPane("List", listView);
        accordion.getPanes().add(titledPane);

        return accordion;
    }

    private Label1 addCanDragLabel(String name){
        Label1 label = new Label1(name);
        label.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                label.setCursor(Cursor.HAND);
                mousePressNode = NodeRegister.getNode(name);
            }
        });
        label.setOnDragDetected(mouseEvent -> {
            label.startFullDrag();
        });
        label.setOnMouseReleased(event -> {
            label.setCursor(Cursor.DEFAULT);
            mousePressNode = null;
        });

        return label;
    }

    private TreeView<Label1> addTree(){
        gui = new RLPane("ROOT");
        TreeItem<Label1> root = addCustomTree("ROOT",gui);

        TreeView<Label1> treeView = new TreeView<>();
        treeView.setRoot(root);

        return treeView;
    }

    private TreeItem<Label1> addCustomTree(String text,RLPane rlNode){
        //添加gui结构
        TreeItem<Label1> item = new TreeItem<>(new Label1(text));
        item.getValue().setRlNode(NodeRegister.getNode(text));

        item.getValue().setOnMouseDragReleased(mouseEvent -> {
            if(mousePressNode != null){
                item.getChildren().add(addCustomTree(mousePressNode.getName(),item,rlNode));
            }
        });
        return item;
    }

    private TreeItem<Label1> addCustomTree(String text,TreeItem<Label1> parent,RLPane rlPane){
        //添加gui结构
        TreeItem<Label1> item = new TreeItem<>(new Label1(text));
        RLNode node = NodeRegister.getNode(text);
        rlPane.getChild().add(node);
        item.getValue().setRlNode(node);

        item.getValue().setOnMouseDragReleased(mouseEvent -> {
            if(mousePressNode != null){
                //todo
                if(node instanceof RLPane rlPane1)
                    item.getChildren().add(addCustomTree(mousePressNode.getName(),item,rlPane1));
            }
        });
        item.getValue().setOnMouseClicked(keyEvent -> {
            flowPane.getChildren().remove(0);
            label1 = item.getValue();
            flowPane.getChildren().add(addAttributeSetting(label1.getRlNode()));

            item.getValue().requestFocus();
        });
        item.getValue().setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.DELETE){
                rlPane.getChild().remove(node);
                parent.getChildren().remove(item);
            }
        });
        return item;
    }

    private FlowPane addAttributeSetting(RLNode rlNode){
        FlowPane flowPane1 = new FlowPane();
        TextField numberField = new TextField();
        numberField.setPromptText("Enter a number");
        numberField.setText(String.valueOf(rlNode.getLayoutX()));
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(oldValue);
            }
            else {
                rlNode.setLayoutX(newValue.equals("")?0:Integer.parseInt(newValue));
            }
        });
        flowPane1.getChildren().add(numberField);

        TextField numberField1 = new TextField();
        numberField1.setPromptText("Enter a number");
        numberField1.setText(String.valueOf(rlNode.getLayoutY()));
        numberField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField1.setText(oldValue);
            }
            else
                rlNode.setLayoutY(newValue.equals("")?0:Integer.parseInt(newValue));
        });
        flowPane1.getChildren().add(numberField1);

        TextField numberField2 = new TextField();
        numberField2.setPromptText("Enter a number");
        numberField2.setText(String.valueOf(rlNode.getWidth()));
        numberField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField2.setText(oldValue);
            }
            else {
                if(rlNode.getRlBackGround()!=null&&rlNode.isLocked()){
                    Image image = rlNode.getRlBackGround().getImage();
                    double hw = image.getHeight()/image.getWidth();
                    int i = newValue.equals("") ? 0 : Integer.parseInt(newValue);
                    rlNode.setWidth(i);
                    rlNode.setHeight((int)(i*hw));
                }
                else
                    rlNode.setWidth(newValue.equals("") ? 0 : Integer.parseInt(newValue));
            }
        });
        flowPane1.getChildren().add(numberField2);

        TextField numberField3 = new TextField();
        numberField3.setPromptText("Enter a number");
        numberField3.setText(String.valueOf(rlNode.getHeight()));
        numberField3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField3.setText(oldValue);
            }
            else
                rlNode.setHeight(newValue.equals("")?0:Integer.parseInt(newValue));
        });
        flowPane1.getChildren().add(numberField3);

        TextField numberField4 = new TextField();
        numberField4.setPromptText("Enter a number");
        numberField4.setText(rlNode.getDisplayName());
        numberField4.textProperty().addListener((observable, oldValue, newValue) -> {
            numberField4.setText(newValue);
            rlNode.setDisplayName(newValue);
        });
        flowPane1.getChildren().add(numberField4);

        Label buttonLoad = new Label("png");
        buttonLoad.setOnMouseClicked((event)-> {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
                try {
                    File file = fileChooser.showOpenDialog(stage).getAbsoluteFile();
                    buttonLoad.setText(file.toString());
                    RLBackGround rlBackGround = new RLBackGround(new Image(file.toString()));
                    rlNode.setRlBackGround(rlBackGround);
                }
                catch (NullPointerException e){

                }
        });
        flowPane1.getChildren().add(buttonLoad);

        Button button = new Button("lock");
        button.setOnMouseClicked((event)-> {
            rlNode.setLocked(!rlNode.isLocked());
        });
        flowPane1.getChildren().add(button);


        return flowPane1;
    }

    private FlowPane addAttribute(){
        Button b1 = new Button();


        FlowPane accordion = new FlowPane(b1);

        return accordion;
    }

    private void drawShapes(GraphicsContext gc,TreeItem<Label1> treeView) {
        RLNode rlNode = treeView.getValue().getRlNode();
        ObservableList<TreeItem<Label1>> children = treeView.getChildren();

        rlNode.render(gc);
        for(TreeItem<Label1> item:children){
            drawShapes(gc,item);
        }
    }

    private void updateShape() {
        gui.update();
        List<RLNode> child = gui.getChild();
        for(RLNode item:child){
            item.update();
        }
    }
}
