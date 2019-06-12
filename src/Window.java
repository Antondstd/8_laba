import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.canvas.*;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Window extends Application {
    
    private Button add, remove, info;
    private Label user;
    private MenuBox menuBox;
    private Canvas canvas;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        BorderPane menu = new BorderPane();
        HBox leftMenu, rightMenu;
        HBox next = new HBox();
        canvas = new Canvas (600,400);
        
        Scene scene = new Scene(root);

        add = new Button("add");
        remove = new Button("remove");
        info = new Button("info");
        user = new Label("User id:");
        menuBox = MenuBox.getAddMenu();

        rightMenu = new HBox();
        leftMenu = new HBox();
        rightMenu.getChildren().addAll(info, user);
        leftMenu.getChildren().addAll(add, remove);
        menu.setLeft(leftMenu);
        menu.setRight(rightMenu);
        next.getChildren().add(menuBox);
        root.getChildren().addAll(menu, next, canvas);

        stage.setScene(scene);

        stage.setTitle("Laba 8");
        stage.setWidth(600);
        stage.setHeight(600);

        stage.show();
    }
}

class MenuBox extends VBox{

    private TextArea x, y, width, name, key, id;
    private Button ok;

    private MenuBox(){
    }

    public static MenuBox getAddMenu(){
        MenuBox box = new MenuBox();
        box.name = new TextArea("Name");
        box.width = new TextArea("Width");
        box.x = new TextArea("x");
        box.y = new TextArea("y");
        box.ok = new Button("ok");
        box.getChildren().addAll(box.name, box.width, box.x, box.y, box.ok);
        return box;
    }

    public static MenuBox getRemoveBox(){
        MenuBox box = new MenuBox();
        box.id = new TextArea("id");
        box.ok = new Button("ok");
        box.getChildren().addAll(box.id, box.ok);
        return box;
    }

    public static MenuBox getRemoveGreater(){
        MenuBox box = new MenuBox();
        box.key = new TextArea("key");
        box.ok = new Button("ok");  
        box.getChildren().addAll(box.key, box.ok);
        return box;
    }
}
