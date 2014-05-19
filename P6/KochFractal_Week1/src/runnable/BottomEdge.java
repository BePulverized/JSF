package runnable;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.concurrent.Task;

/**
 * In this class you can find all properties and operations for BottomEdge.
 * //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/23
 */
public class BottomEdge implements  Runnable, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal kochFractal;
    private final KochManager kochManager;
    private final List<Edge> edges;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(kochFractal, superClass, lvl)">
    /**
     * This is the constructor for BottomEdge.
     *
     * @param kochManager
     * @param kochFractal
     * @param lvl
     */
    public BottomEdge(KochManager kochManager, KochFractal kochFractal, int lvl) {
        this.kochManager = kochManager;
        this.kochFractal = kochFractal;
        this.kochFractal.setLevel(lvl);
        this.kochFractal.addObserver(this);
        edges = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        Task tLeft = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                kochFractal.generateBottomEdge();
                kochManager.plus();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Done!");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                updateMessage("Cancelled!");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage("Failed!");
            }
        };
        new Thread(tLeft).start();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        kochManager.updateEdges((Edge) arg);
    }
    //</editor-fold>
    //</editor-fold>
}
