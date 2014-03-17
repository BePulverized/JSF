//<editor-fold defaultstate="collapsed" desc="Jibberish">
package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class Fractal_01 extends Application {
    
    //<editor-fold defaultstate="collapsed" desc="Declarations">
    
    //</editor-fold>
    
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Start(primaryStage)">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main(args)">
    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
     * main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //</editor-fold>
    //</editor-fold>
}
