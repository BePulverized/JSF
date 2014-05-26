/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kochFractal_week3;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class KochReadWrite extends Application
{
    
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        KochFractal koch = new KochFractal();
            koch.setLevel(1);
            
            koch.generateBottomEdge();
            koch.generateLeftEdge();
            koch.generateRightEdge();     
            koch.writeEdge(koch.getEdges());
           
            System.out.println(koch.getEndTime());
            System.exit(0);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
