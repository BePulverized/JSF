//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week3;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochReadWrite.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/24
 */
public class KochReadWrite extends Application {

    //<editor-fold defaultstate="collapsed" desc="start(primaryStage)">

    @Override
    public void start(Stage primaryStage) throws IOException {
        KochFractal koch = new KochFractal();
        koch.setLevel(1);

        koch.generateBottomEdge();
        koch.generateLeftEdge();
        koch.generateRightEdge();
        koch.writeEdges(koch.getEdges());

        System.out.println(koch.getEndTime());
        System.exit(0);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="static main">
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
}
