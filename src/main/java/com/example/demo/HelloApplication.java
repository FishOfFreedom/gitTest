package com.example.demo;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public Image[] images;

    @Override
    public void init() throws Exception {
        super.init();
        Image image1 = new Image(HelloApplication.class.getResource("textures/main.png").toString());
        Image image2 = new Image(HelloApplication.class.getResource("textures/mianm.png").toString());
        Image image3 = new Image(HelloApplication.class.getResource("textures/main2.png").toString());
        Image image4 = new Image(HelloApplication.class.getResource("textures/main3.png").toString());
        Image image5 = new Image(HelloApplication.class.getResource("textures/main4.png").toString());
        images = new Image[]{image1,image2,image3,image4,image5};
    }

    @Override
    public void start(Stage stage) throws IOException {

        ImageView imageView = new ImageView(images[3]);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.33));
        imageView.fitHeightProperty().bind(stage.heightProperty());

        Pagination pagination1 = new Pagination(5);
        Pagination pagination2 = new Pagination(5);

        HBox root = new HBox();
        root.getChildren().addAll(pagination1,imageView, pagination2);
        HBox.setHgrow(pagination1, Priority.ALWAYS);
        HBox.setHgrow(pagination2, Priority.ALWAYS);

        pagination2.setPageFactory(pageIndex -> createPage(pageIndex,imageView));
        pagination1.setPageFactory(pageIndex -> createPage(pageIndex,imageView));


        Scene scene = new Scene(root, 1024, 640);
        //scene.setFill(null);
        stage.setTitle("JavaFX Background Image Example");
        stage.setResizable(false);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public VBox createPage(int pageIndex,ImageView imageView) {

        VBox box = new VBox(5);
        for(int i = 0;i<5;i++){
            HBox test = new HBox();
            Button button = new Button(String.valueOf(i));
            box.getChildren().add(test);

            int i1 = i;
            final BooleanProperty isMouseOver = new BooleanPropertyBase(false) {
                @Override
                protected void invalidated() {
                    super.invalidated();
                    // 根据isMouseOver的值更新ImageView的图片
                    if (get()) {
                        imageView.setImage(images[i1]);
                    } else {
                        imageView.setImage(images[0]);
                    }
                }

                @Override
                public Object getBean() {
                    return button;
                }

                @Override
                public String getName() {
                    return "isMouseOver";
                }
            };

            button.setOnAction(actionEvent -> System.out.println(1));

            test.setOnMouseEntered(event -> isMouseOver.set(true));

            test.setOnMouseExited(event -> isMouseOver.set(false));
            test.getChildren().add(button);
        }
        return box;
    }
}