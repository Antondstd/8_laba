import javafx.application.Application;
import javafx.event.*;
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
    
    private Button add, remove, removeGreater, info;
    private Label user;
    private MenuBox menuBox;
    private Table table;
    private Canvas canvas;

    private Client client;
    private String password, email;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void add(String key, String name, int weight, int x, int y){
        client.add(key, name, weight, x, y, email, password);
    }

    public void remove(int id){
        client.remove(id, email, password);
    }

    public void removeGreater(String key){
        client.removeGreater(key, email, password);
    }
    
    @Override
    public void init(){
        client = new Client();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        BorderPane menu = new BorderPane();
        HBox leftMenu, rightMenu;
        HBox next = new HBox();
        table = new Table();
        canvas = new Canvas (600,400);
        
        Scene scene = new Scene(root);

        Window win = this;
        add = new Button("add");
        add.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                next.getChildren().removeAll(menuBox, table);
                menuBox = MenuBox.getAddMenu(win);
                next.getChildren().addAll(menuBox, table);
            }
        });
        remove = new Button("remove");
        remove.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                next.getChildren().removeAll(menuBox, table);
                menuBox = MenuBox.getRemoveMenu(win);
                next.getChildren().addAll(menuBox, table);
            }
        });
        removeGreater = new Button("removeGreater");
        removeGreater.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                next.getChildren().removeAll(menuBox, table);
                menuBox = MenuBox.getRemoveGreaterMenu(win);
                next.getChildren().addAll(menuBox, table);
            }
        });
        info = new Button("info");
        user = new Label("User id:");
        menuBox = MenuBox.getAddMenu(this);

        rightMenu = new HBox();
        leftMenu = new HBox();
        rightMenu.getChildren().addAll(info, user);
        leftMenu.getChildren().addAll(add, remove, removeGreater);
        menu.setLeft(leftMenu);
        menu.setRight(rightMenu);
        next.getChildren().addAll(menuBox, table);
        root.getChildren().addAll(menu, next, canvas);

        stage.setScene(scene);

        stage.setTitle("Laba 8");
        stage.setWidth(600);
        stage.setHeight(600);

        stage.show();
    }
}

class MenuBox extends VBox {

    private TextArea x, y, width, name, key, id;
    private Button ok;

    private MenuBox(){
    }

    public static MenuBox getAddMenu(Window handler){
        MenuBox box = new MenuBox();
        box.key = new TextArea("Key");
        box.name = new TextArea("Name");
        box.width = new TextArea("Width");
        box.x = new TextArea("x");
        box.y = new TextArea("y");
        box.ok = new Button("ok");
        box.ok.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                handler.add(box.key.getText(), box.name.getText(), Integer.parseInt(box.width.getText()), Integer.parseInt(box.x.getText()), Integer.parseInt(box.y.getText()));
            }   
        });
        box.getChildren().addAll(box.key, box.name, box.width, box.x, box.y, box.ok);
        return box;
    }

    public static MenuBox getRemoveMenu(Window handler){
        MenuBox box = new MenuBox();
        box.id = new TextArea("id");
        box.ok = new Button("ok");
        box.ok.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                handler.remove(Integer.parseInt(box.id.getText()));
            }   
        });
        box.getChildren().addAll(box.id, box.ok);
        return box;
    }

    public static MenuBox getRemoveGreaterMenu(Window handler){
        MenuBox box = new MenuBox();
        box.key = new TextArea("key");
        box.ok = new Button("ok");  
        box.ok.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                handler.removeGreater(box.key.getText());
            }   
        });
        box.getChildren().addAll(box.key, box.ok);
        return box;
    }
}

class Table extends TableView<Human>{

    private TableColumn<Human, String> name;
    private TableColumn<Human, Integer> x, y, weight;   

    Table(){
        name = new TableColumn("Name");
        x = new TableColumn("X");
        y = new TableColumn("Y");
        weight = new TableColumn("weight");
        getColumns().addAll(name, weight, x, y);
    }

}
