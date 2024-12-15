package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane an = new AnchorPane();

        //在 TabPane 上放 Tab
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color:#fffff0");

        tabPane.setPrefWidth(300);
        tabPane.setPrefHeight(300);
        Tab tab1 = new Tab("tab1");//tab无法设置宽高
        Tab tab2 = new Tab("tab2");
        Tab tab3 = new Tab("tab3");

        HBox hBox = new HBox(10);//间距为10
        hBox.setAlignment(Pos.CENTER);
        //背景类(颜色,圆角,内边距)
        Background background1 = new Background(new BackgroundFill(Paint.valueOf("#FFF0F5"),new CornerRadii(10.0),new Insets(10)));
        hBox.setBackground(background1);
        hBox.getChildren().addAll(new Button("button1"),new Button("button2"));
        tab1.setContent(hBox);

        Background background2 = new Background(new BackgroundFill(Paint.valueOf("#F0FFF0"),new CornerRadii(3.0),new Insets(5)));
        //边框类 颜色 样式 圆角 宽度
        Border bor = new Border(new BorderStroke(Paint.valueOf("#FF82AB"),BorderStrokeStyle.DOTTED,new CornerRadii(3.0),new BorderWidths(5)));
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(background2);
        vBox.setBorder(bor);
        vBox.getChildren().addAll(new Button("button3"),new Button("button4"));
        tab2.setContent(vBox);

        AnchorPane an2 = new AnchorPane();
        an2.setStyle("-fx-background-color:#F0E68C");
        Button button5 = new Button("button5");
        AnchorPane.setLeftAnchor(button5,10.0);
        AnchorPane.setTopAnchor(button5,10.0);
        an2.getChildren().addAll(button5);
        tab3.setContent(an2);


        tabPane.getTabs().addAll(tab1,tab2,tab3);
        an.getChildren().addAll(tabPane);
        AnchorPane.setTopAnchor(tabPane,100.0);
        AnchorPane.setLeftAnchor(tabPane,100.0);


        Scene scene = new Scene(an);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.setTitle("JavaFX");

        ImageView img = new ImageView(HelloApplication.class.getResource("textures/main.png").toString());
        tab1.setGraphic(img);
//        tabPane.setRotateGraphic(false);//图片方向跟随
        //设置朝向(位置)
//        tabPane.setSide(Side.RIGHT);
        primaryStage.show();

        //打开和关闭
//        tab1.setClosable(false);//禁止tab1关闭
//        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);//都不允许关闭
        //禁用tab组件
//        tab3.setDisable(true);
        //默认选中
//        tabPane.getSelectionModel().select(tab2);//默认选中
//        tabPane.getSelectionModel().selectLast();//默认选中最后一个

        //监听tabpane组件的改变
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println("这是 = " + newValue.getText());
            }
        });

        //监听tab的状态改变
        tab1.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Tab t = (Tab)event.getSource();
                System.out.println("tab1被改变了 - " + t.getText());
            }
        });

        an.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //点击就添加一个tab
            @Override
            public void handle(MouseEvent event) {
                tabPane.getTabs().add(new Tab("新tab"));
            }
        });

        //下面这两个都对tab的关闭做出响应,如果不设置event.consume()的话
        tab1.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("tab1.setOnClosed");
            }
        });

        tab1.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("tab1.setOnCloseRequest");
                event.consume();//阻止事件传递("超纲"),只有这里做出响应
            }
        });

    }
}

