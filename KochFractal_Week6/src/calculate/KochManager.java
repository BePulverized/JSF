//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractal.fx.JSF31KochFractalFX;
import runnable.BottomEdge;
import runnable.LeftEdge;
import runnable.RightEdge;
import timeutil.TimeStamp;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochObserver.
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/19
 */
public class KochManager implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final JSF31KochFractalFX application;
    private final KochFractal koch;
    private final ArrayList<Edge> edges;
    private int counter;
    public TimeStamp ts;
    //</editor-fold>   

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for KochObserver.
     *
     * @param application
     */
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.koch = new KochFractal();
        this.ts = new TimeStamp();

        edges = new ArrayList<>();
    }
    //</editor-fold>

    public synchronized void changeLevel(int level) {
        koch.setLevel(level);
        edges.clear();

        ts.setBegin();

        counter = 0;
        Thread bottomThread = new Thread(new BottomEdge(this, new KochFractal(), level));
        bottomThread.start();

        Thread leftThread = new Thread(new LeftEdge(this, new KochFractal(), level));
        leftThread.start();

        Thread rightThread = new Thread(new RightEdge(this, new KochFractal(), level));
        rightThread.start();
    }

    public synchronized void plus(){
        counter++;
        if(counter >= 3){
            ts.setEnd();
            application.setTextCalc(ts.toString());
            application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
            
            application.requestDrawEdges();
        }       
    }

    public synchronized void drawEdges() {
        ts = new TimeStamp();
        ts.setBegin();
        application.clearKochPanel();

        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts.setEnd();
        application.setTextDraw(ts.toString());
    }

    public synchronized void updateEdges(Edge e) {
        edges.add(e);
    }

    @Override
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge) arg);
        edges.add((Edge) arg);
    }

    //</editor-fold>
}
