package week2opd2metgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * In this class you can find all properties and operations for Week2OPD2MetGUI.
 *
 * @organization: Moridrin
 * @author phinux
 * @date 15-mei-2014
 */
public class Week2OPD2MetGUI extends Application {
    //<editor-fold defaultstate="collapsed" desc="Declarations">
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor/Start">
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operations">
    
    //</editor-fold>
}
