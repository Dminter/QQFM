package com.zncm.qqfm.main;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author spurksky
 */
public class JfxPlayer extends Application {
    
    public static final String path = "D:\\Dev\\QQFM\\music\\韩国人为什么喜欢穿白色衣服.mp3";
    public static Button play = new Button();    
    
    @Override
    public void start(Stage primaryStage) {
        
        play.setText("♫");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                play.setText("Pause");
            }            
        });
        Button stop = new Button();
        stop.setText("Stop");
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //code here
            }            
        });
        Button prev = new Button();
        prev.setText("<<");
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //code here
            }            
        });
        Button next = new Button();
        next.setText(">>");
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //code here
            }            
        });
        
        StackPane root = new StackPane();
        StackPane.setMargin(prev, new Insets(10.0, 10.0, 10.0, -150.0));
        StackPane.setMargin(play, new Insets(10.0, 10.0, 10.0, -50.0));
        StackPane.setMargin(stop, new Insets(10.0, 10.0, 10.0, 50.0));
        StackPane.setMargin(next, new Insets(10.0, 10.0, 10.0, 150.0));
        root.getChildren().addAll(prev,play,stop,next);
        root.setAlignment(Pos.TOP_CENTER);
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });*/
        
        Scene scene = new Scene(root, 300, 250);
        
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        
        primaryStage.setTitle("JFX Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}