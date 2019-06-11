import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Login extends Application {
    public static void main(String[] args) {

        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        try {
            Settings.address = InetAddress.getByName("localhost");
            Settings.socket = new DatagramSocket();
        }catch (Exception e){

        }
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Login");
        stage.setWidth(495);
        stage.setHeight(300);

        stage.show();
    }
}
