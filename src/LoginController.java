import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.DatagramPacket;

public class LoginController {
    @FXML
    private Button in;
    @FXML TextField Login;
    @FXML TextField Password;
    @FXML
    protected void Registration(ActionEvent event) {
        System.out.println("reg " + Login.textProperty().getValue() + " " + Password.textProperty().getValue());
        Sendi sendi = new Sendi();
        sendi.fromClient = "reg " + Login.textProperty().getValue();
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();
            Settings.buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(Settings.buffer,Settings.buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(Settings.bufferResponce, Settings.bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            Settings.socket.receive(response);
            ByteArrayInputStream bis = new ByteArrayInputStream(Settings.bufferResponce);
            ObjectInput in = null;
            Sendi sendiReceive;

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText("Results:");
            alert.setContentText(sendiReceive.fromServer);
            System.out.println(sendiReceive.fromServer);

            alert.showAndWait();
        }
        catch (IOException ex) {
        }
        catch (Exception e){

        }

    }
    @FXML
    protected void LogIn(ActionEvent event) {
        Sendi sendi = new Sendi();
        String login = Login.textProperty().getValue();
        String password = Password.textProperty().getValue();
        sendi.fromClient = "auth " + Login.textProperty().getValue() + " " + Password.textProperty().getValue();
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();
            Settings.buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(Settings.buffer,Settings.buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(Settings.bufferResponce, Settings.bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            Settings.socket.receive(response);
            ByteArrayInputStream bis = new ByteArrayInputStream(Settings.bufferResponce);
            ObjectInput in = null;
            Sendi sendiReceive;

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign in");
            alert.setHeaderText("Results:");
            alert.setContentText(sendiReceive.fromServer);
            System.out.println(sendiReceive.fromServer);

            alert.showAndWait();
            if (sendiReceive.fromServer.equals("Успешная авторизация")){
                Settings.email = login;
                Settings.password = password;
            }
        }
        catch (IOException ex) {
        }
        catch (Exception e){

        }

    }

}
