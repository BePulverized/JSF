//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractal.fx.JSF31KochFractalFX;
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
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
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
        this.koch.addObserver(this);
        edges = new ArrayList<>();
    }
    //</editor-fold>

    public void changeLevel(int nxt) {
        koch.setLevel(nxt);
        edges.clear();

        //OPD 5
        TimeStamp ts = new TimeStamp();
        ts.setBegin();

        drawEdges();

        ts.setEnd();
        application.setTextCalc(ts.toString());

        //OPD 6
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }

    public void drawEdges() {
        //OPD 3
        application.clearKochPanel();
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();

        //OPD 4
        for (Edge e : edges) {
            application.drawEdge(e);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        //opd 3:
        //application.drawEdge((Edge) arg);

        //opd 4:
        edges.add((Edge) arg);
    }

    //</editor-fold>
}
