//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

//</editor-fold>
import interfaces.SuperClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import runnable.BottomEdge;
import runnable.LeftEdge;
import runnable.RightEdge;

/**
 * In this class you can find all properties and operations for KochManager.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/18
 */
public class KochManager implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final SuperClass SUPERCLASS;
    private final KochFractal KOCHFRACTAL;
    private List<Edge> edges;
    private Thread bottonEdgeThread;
    private Thread leftEdgeThread;
    private Thread rightEdgeThread;
//</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(superApplication)">
    /**
     * This is the constructor for KochManager.
     *
     * @param superApplication is the application that creates this class.
     */
    public KochManager(SuperClass superApplication) {
        this.SUPERCLASS = superApplication;
        this.KOCHFRACTAL = new KochFractal();
        this.KOCHFRACTAL.addObserver(this);
        edges = new ArrayList<>();
    }
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setLevel(lvl)">
    public void setLevel(int lvl) {
        KOCHFRACTAL.setLevel(lvl);
        drawEdges();
        edges.clear();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdges()">
    public void drawEdges() {
        SUPERCLASS.preformAction("disableButton", null);
        SUPERCLASS.preformAction("clearKochPanel", null);
        bottonEdgeThread = new Thread(new BottomEdge(KOCHFRACTAL));
        bottonEdgeThread.start();
        leftEdgeThread = new Thread(new LeftEdge(KOCHFRACTAL));
        leftEdgeThread.start();
        rightEdgeThread = new Thread(new RightEdge(KOCHFRACTAL));
        rightEdgeThread.start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public synchronized void update(Observable o, Object arg) {
        Object[] args = new Object[1];
        args[0] = arg;
        SUPERCLASS.preformAction("drawEdge", args);
    }
    //</editor-fold>
    //</editor-fold>
}
