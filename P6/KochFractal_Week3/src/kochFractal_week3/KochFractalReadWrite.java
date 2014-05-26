//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochFractalReadWrite.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/24
 */
public class KochFractalReadWrite extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;

    // Koch manager
    private KochManager kochManager;

    // Current level of Koch fractal
    private int currentLevel = 1;

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
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="start(primaryStage)">
    @Override
    public void start(Stage primaryStage) throws IOException {
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
        labelLevel = new Label("Level: " + currentLevel);
        grid.add(labelLevel, 0, 6);

        // Button to increase level of Koch fractal
        Button buttonIncreaseLevel = new Button();
        buttonIncreaseLevel.setText("Increase Level");
        buttonIncreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                increaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonIncreaseLevel, 3, 6);

        // Button to decrease level of Koch fractal
        Button buttonDecreaseLevel = new Button();
        buttonDecreaseLevel.setText("Decrease Level");
        buttonDecreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decreaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonDecreaseLevel, 5, 6);

        // Button to fit Koch fractal in Koch panel
        Button buttonFitFractal = new Button();
        buttonFitFractal.setText("Fit Fractal");
        buttonFitFractal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    fitFractalButtonActionPerformed(event);
                } catch (IOException ex) {
                    Logger.getLogger(KochFractalReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        grid.add(buttonFitFractal, 14, 6);

        // Add mouse clicked event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                  new EventHandler<MouseEvent>() {
                                      @Override
                                      public void handle(MouseEvent event) {
                                          try {
                                              kochPanelMouseClicked(event);
                                          } catch (IOException ex) {
                                              Logger.getLogger(KochFractalReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                                          }
                                      }
                                  });

        // Add mouse pressed event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED,
                                  new EventHandler<MouseEvent>() {
                                      @Override
                                      public void handle(MouseEvent event) {
                                          kochPanelMousePressed(event);
                                      }
                                  });

        // Add mouse dragged event to Koch panel
        kochPanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    kochPanelMouseDragged(event);
                } catch (IOException ex) {
                    Logger.getLogger(KochFractalReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Create Koch manager and set initial level
        resetZoom();
        kochManager = new KochManager(this);

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, kpWidth + 50, kpHeight + 170);
        root.getChildren().add(grid);

        // Define title and assign the scene for main window
        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        kochManager.drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearKochPanel()">
    public void clearKochPanel() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, kpWidth, kpHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0.0, 0.0, kpWidth, kpHeight);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdge(e)">
    public void drawEdge(Edge e) {
        // Graphics
        GraphicsContext gc = kochPanel.getGraphicsContext2D();

        // Adjust edge for zoom and drag
        Edge e1 = edgeAfterZoomAndDrag(e);

        // Set line color
        // Set line width depending on level
        if (currentLevel <= 3) {
            gc.setLineWidth(2.0);
        } else if (currentLevel <= 5) {
            gc.setLineWidth(1.5);
        } else {
            gc.setLineWidth(1.0);
        }

        // Draw line
        gc.strokeLine(e1.X1, e1.Y1, e1.X2, e1.Y2);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextNrEdges(text)">
    public void setTextNrEdges(String text) {
        labelNrEdgesText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextCalc(text)">
    public void setTextCalc(final String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelCalcText.setText(text);
                setTextDraw("");
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextDraw(text)">
    public void setTextDraw(String text) {
        labelDrawText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requestDrawEdges()">
    public void requestDrawEdges() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    kochManager.drawEdges();
                } catch (IOException ex) {
                    Logger.getLogger(KochFractalReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resetZoom()">
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="edgeAfterZoomAndDrag(e)">
    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Events">
    //<editor-fold defaultstate="collapsed" desc="increaseLevelButtonActionPerformed(event)">
    private void increaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel < 12) {
            // resetZoom();
            currentLevel++;
            labelLevel.setText("Level: " + currentLevel);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="decreaseLevelButtonActionPerformed(event)">
    private void decreaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel > 1) {
            // resetZoom();
            currentLevel--;
            labelLevel.setText("Level: " + currentLevel);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fitFractalButtonActionPerformed(event)">
    private void fitFractalButtonActionPerformed(ActionEvent event) throws IOException {
        resetZoom();
        kochManager.drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMouseClicked(event)">
    private void kochPanelMouseClicked(MouseEvent event) throws IOException {
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
            kochManager.drawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMouseDragged(event)">
    private void kochPanelMouseDragged(MouseEvent event) throws IOException {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        kochManager.drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMousePressed(event)">
    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
    }
    //</editor-fold>
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
