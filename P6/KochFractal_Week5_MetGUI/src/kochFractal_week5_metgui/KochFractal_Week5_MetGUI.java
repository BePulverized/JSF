//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week5_metgui;

import callculate.Edge;
import enums.CommunicationType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import remote.ServerConnecter;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochFractal_Week5_MetGUI. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class KochFractal_Week5_MetGUI extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;

    // Current level of Koch fractal
    private int level = 1;

    // Labels for level, nr edges, calculation time, and drawing time
    private Label labelLevel;
    private Label labelNrEdges;
    private Label labelNrEdgesText;
    private Label labelCalc;
    private Label labelCalcText;
    private Label labelDraw;
    private Label labelDrawText;

    // Koch panel and its size
    private Canvas kochPanel;
    private final int kpWidth = 500;
    private final int kpHeight = 500;
    private final List<Edge> edges = new ArrayList<>();

    //Connecter
    private ServerConnecter serverConnecter;
    private CommunicationType communicationType;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Start">
    @Override
    public void start(Stage primaryStage) {

        // Define grid pane
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // For debug purposes
        // Make de grid lines visible
        // grid.setGridLinesVisible(true);
        // Drawing panel for Koch fractal
        kochPanel = new Canvas(kpWidth, kpHeight);
        grid.add(kochPanel, 0, 3, 25, 1);

        // Labels to present number of edges for Koch fractal
        labelNrEdges = new Label("Nr edges:");
        labelNrEdgesText = new Label();
        grid.add(labelNrEdges, 0, 0, 4, 1);
        grid.add(labelNrEdgesText, 3, 0, 22, 1);

        // Labels to present time of calculation for Koch fractal
        labelCalc = new Label("Calculating:");
        labelCalcText = new Label();
        grid.add(labelCalc, 0, 1, 4, 1);
        grid.add(labelCalcText, 3, 1, 22, 1);

        // Labels to present time of drawing for Koch fractal
        labelDraw = new Label("Drawing:");
        labelDrawText = new Label();
        grid.add(labelDraw, 0, 2, 4, 1);
        grid.add(labelDrawText, 3, 2, 22, 1);

        // Label to present current level of Koch fractal
        labelLevel = new Label("Level: " + level);
        grid.add(labelLevel, 0, 6);

        // Button to increase level of Koch fractal
        Button buttonIncreaseLevel = new Button();
        buttonIncreaseLevel.setText("Increase Level");
        buttonIncreaseLevel.setOnAction((ActionEvent event) -> {
            increaseLevelButtonActionPerformed(event);
        });
        grid.add(buttonIncreaseLevel, 3, 6);

        // Button to decrease level of Koch fractal
        Button buttonDecreaseLevel = new Button();
        buttonDecreaseLevel.setText("Decrease Level");
        buttonDecreaseLevel.setOnAction((ActionEvent event) -> {
            decreaseLevelButtonActionPerformed(event);
        });
        grid.add(buttonDecreaseLevel, 5, 6);

        // Button to fit Koch fractal in Koch panel
        Button buttonFitFractal = new Button();
        buttonFitFractal.setText("Fit Fractal");
        buttonFitFractal.setOnAction((ActionEvent event) -> {
            fitFractalButtonActionPerformed(event);
        });
        grid.add(buttonFitFractal, 14, 6);

        // Add mouse clicked event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            kochPanelMouseClicked(event);
        });

        // Add mouse pressed event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            kochPanelMousePressed(event);
        });

        // Add mouse dragged event to Koch panel
        kochPanel.setOnMouseDragged((MouseEvent event) -> {
            kochPanelMouseDragged(event);
        });

        // Create Koch manager and set initial level
        resetZoom();

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, kpWidth + 50, kpHeight + 170);
        root.getChildren().add(grid);

        // Define title and assign the scene for main window
        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });

        this.serverConnecter = new ServerConnecter();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Draw methodes">
    public void clearKochPanel() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, kpWidth, kpHeight);
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0, 0.0, kpWidth, kpHeight);
    }

    public void drawEdges() {
        clearKochPanel();
        for (Edge edge : edges) {
            drawEdge(edge);
        }
    }

    public void drawEdge(Edge e) {
        // Graphics
        GraphicsContext gc = kochPanel.getGraphicsContext2D();

        // Adjust edge for zoom and drag
        Edge e1 = edgeAfterZoomAndDrag(e);

        // Set line color
        gc.setStroke(e1.getColor());

        // Set line width depending on level
        if (level <= 3) {
            gc.setLineWidth(2.0);
        } else if (level <= 5) {
            gc.setLineWidth(1.5);
        } else {
            gc.setLineWidth(1.0);
        }

        // Draw line
        gc.strokeLine(e1.X1, e1.Y1, e1.X2, e1.Y2);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get(level)">
    public void get(int level) {
        String message;
        serverConnecter.connect();

        //See it you connect to a fractal server
        message = "Can you please calculate a Fractal for me?";
        serverConnecter.sendObjectToServer(message);
        message = serverConnecter.readMessageFromServer();
        if (!message.toLowerCase().contains("what level")) {
            return;
        }

        //Say what level the factal needs to be
        serverConnecter.sendObjectToServer(level);
        if (communicationType == CommunicationType.Minimal_Network_Load) {
            //Say at what speed you want tog get the edges
            message = "Take you're time, and send it when you're finished calculating.";
            serverConnecter.sendObjectToServer(message);

            //Get the edges
            clearKochPanel();
            edges.clear();
            edges.addAll((Collection<? extends Edge>) serverConnecter.readObjectFromServer());
        } else {
            //Say at what speed you want tog get the edges
            message = "I need each edge as fast as possible!";
            serverConnecter.sendObjectToServer(message);

            //Clear Local
            clearKochPanel();
            edges.clear();
            
            //Ask if the server has the list in cash.
            message = "Do you have it in cash?";
            serverConnecter.sendObjectToServer(message);

            //See if the edges are in cash
            message = serverConnecter.readMessageFromServer();
            if (message.toLowerCase().contains("yes")) {
                
                //Get the edges
                edges.addAll((Collection<? extends Edge>) serverConnecter.readObjectFromServer());
            } else {
                
                //Get the edges
                int totalEdges = (int) serverConnecter.readObjectFromServer();
                for (int i = 0; i < totalEdges; i++) {
                    Edge edge = (Edge) serverConnecter.readObjectFromServer();
                    drawEdge(edge.getWhite());
                    edges.add(edge);
                }
            }

        }

        //Thank the server
        message = "Thanks for calculating that for me!";
        serverConnecter.sendObjectToServer(message);
        message = serverConnecter.readMessageFromServer();
        if (!message.toLowerCase().contains("you") || !message.toLowerCase().contains("welcome")) {
            message = "You could say something as \"You're Welcome!\"";
            serverConnecter.sendObjectToServer(message);
        }
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setTextNrEdges(String text) {
        labelNrEdgesText.setText(text);
    }

    public void setTextCalc(String text) {
        labelCalcText.setText(text);
    }

    public void setTextDraw(String text) {
        labelDrawText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Events">
    //<editor-fold defaultstate="collapsed" desc="Level Increase">
    private void increaseLevelButtonActionPerformed(ActionEvent event) {
        if (level < 10) {
            level++;
            get(level);
            labelLevel.setText("Level: " + level);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Level Decrease">
    private void decreaseLevelButtonActionPerformed(ActionEvent event) {
        if (level > 1) {
            level--;
            get(level);
            labelLevel.setText("Level: " + level);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Fit Button">
    private void fitFractalButtonActionPerformed(ActionEvent event) {
        resetZoom();
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panel Clicked">
    private void kochPanelMouseClicked(MouseEvent event) {
        if (Math.abs(event.getX() - startPressedX) < 1.0
            && Math.abs(event.getY() - startPressedY) < 1.0) {
            double originalPointClickedX = (event.getX() - zoomTranslateX) / zoom;
            double originalPointClickedY = (event.getY() - zoomTranslateY) / zoom;
            if (event.getButton() == MouseButton.PRIMARY) {
                zoom *= 2.0;
            } else if (event.getButton() == MouseButton.SECONDARY) {
                zoom /= 2.0;
            }
            zoomTranslateX = (int) (event.getX() - originalPointClickedX * zoom);
            zoomTranslateY = (int) (event.getY() - originalPointClickedY * zoom);
            drawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panel Dragged">
    private void kochPanelMouseDragged(MouseEvent event) {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Pressed">
    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
    }
    //</editor-fold>
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Zoom">
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }

    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY,
                e.getColor());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main(args)">
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
