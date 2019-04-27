import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));

        Label userName = new Label("Username");
        Label message = new Label ("Message");
        Button send = new Button ("Send");
        Button exit = new Button("Exit");
        TextField userNameTF = new TextField();
        TextField msgTF = new TextField();

        gp.add(userName, 0, 0);
        gp.add(message, 0, 1);
        gp.add(send, 0, 2);
        gp.add(exit, 0, 3);
        gp.add(userNameTF, 1, 0);
        gp.add(msgTF, 1, 1);
        GridPane.setMargin(userNameTF, new Insets(10, 10, 10, 10));
        GridPane.setMargin(msgTF, new Insets(10, 10, 10, 10));
        GridPane.setMargin(send, new Insets(10, 10, 10, 0));
        GridPane.setMargin(exit, new Insets(10, 10, 10, 0));

        String clientName = "Client ";
        String host = "127.0.0.1";
        int port = 20500;

        Socket socket = null;
        InputStream io = null;
        OutputStream os = null;

        try {
            socket = new Socket(host, port);
            io = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(os, true);

        Scanner streamScanner = new  Scanner(io);
        System.out.println("Connectted to " +  host + ":" + port);
        System.out.print(streamScanner.nextLine());

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pw.println(userNameTF.getText() + ": " + msgTF.getText());
                System.out.println("\n" + streamScanner.nextLine());
            }
        });

        InputStream finalIo = io;
        Socket finalSocket = socket;
        OutputStream finalOs = os;
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    finalSocket.close();
                    finalIo.close();
                    finalOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }
        });

        Scene scene = new Scene(gp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
