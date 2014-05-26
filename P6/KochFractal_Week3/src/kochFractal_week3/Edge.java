//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week3;

import java.io.Serializable;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Edge.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/24
 */
public class Edge implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    public double X1, Y1, X2, Y2;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(X1, Y1, X2, Y2)">
    public Edge(double X1, double Y1, double X2, double Y2) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
    }
    //</editor-fold>

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Class">
    @Override
    public String toString() {
        return X1 + "," + Y1 + "," + X2 + "," + Y2;
    }
    //</editor-fold>
}
