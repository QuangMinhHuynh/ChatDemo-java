import javafx.application.Application;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Application implements Runnable {
    private Socket socket;
    private InputStream io;
    private OutputStream os;
    private PrintWriter pw;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    ServerThread(Socket socket){
        this.socket = socket;
    }

    public long getId(){
        return Thread.currentThread().getId();
    }

    @Override
    public void run(){
        try {
            io = socket.getInputStream();
            os = socket.getOutputStream();

            try (Scanner scanner = new Scanner(io)){
                pw = new PrintWriter(os, true);
                pw.println("User connected");

                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    Server.bbs.appendText(line + "\n");
                    pw.println("Echo: " + line);
                }
            } finally {
                System.out.println("Client " + getId() + " has disconnected");
                pw.close();
                io.close();
                os.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}