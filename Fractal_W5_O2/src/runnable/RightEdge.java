//<editor-fold defaultstate="collapsed" desc="Jibberish">
package runnable;

//</editor-fold>
import calculate.KochFractal;

/**
 * In this class you can find all properties and operations for RightEdge. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/23
 */
public class RightEdge implements Runnable {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal KOCHFRACTAL;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for RightEdge.
     *
     * @param kochFractal
     */
    public RightEdge(KochFractal kochFractal) {
        this.KOCHFRACTAL = kochFractal;
    }
    //</editor-fold>

    @Override
    public void run() {
        KOCHFRACTAL.generateRightEdge();
    }
    //</editor-fold>
}
