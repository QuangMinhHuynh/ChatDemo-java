import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Application {

    static TextArea bbs = new TextArea("");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vb = new VBox();
        vb.setPadding(new Insets(10,10,10,10));
        bbs.setWrapText(true);
        bbs.setPrefHeight(250);
        bbs.setEditable(false);

        Button exit = new Button("Exit");

        vb.getChildren().addAll(bbs, exit);

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        new Thread(() ->{
            try (ServerSocket serverSocket = new ServerSocket(20500)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    Runnable r = new ServerThread(socket);
                    Thread t = new Thread(r);
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        Scene scene = new Scene(vb, 150, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
