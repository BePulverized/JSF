//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochManager.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/24
 */
public class KochManager {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractalReadWrite application;
    private final ArrayList<Edge> edges;
    private Scanner scan;
    private Edge edge;
    private double beginTime;
    private double endTime = 0;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(application)">
    public KochManager(KochFractalReadWrite application) throws IOException {
        this.edges = new ArrayList<>();
        this.application = application;
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdges()">
    public void drawEdges() throws FileNotFoundException, IOException {
        application.clearKochPanel();
        edges.clear();
        beginTime = System.currentTimeMillis();

        RandomAccessFile memoryMappedFile = new RandomAccessFile("EdgeFile.txt", "rw");
        int level = memoryMappedFile.readInt();
        int nrEdges = memoryMappedFile.readInt();

        for (int i = 0; i < nrEdges; i++) {
            double X1 = memoryMappedFile.readDouble();
            double Y1 = memoryMappedFile.readDouble();
            double X2 = memoryMappedFile.readDouble();
            double Y2 = memoryMappedFile.readDouble();
            edge = new Edge(X1, Y1, X2, Y2);
            edges.add(edge);
        }

        System.out.println("Reading from Memory mapped file is completed.");

        endTime = (System.currentTimeMillis() - beginTime) / 1000;
        application.setTextCalc(endTime + " sec");

        for (Edge e : edges) {
            application.drawEdge(e);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawBufferedEdges()">
    public void drawBufferedEdges() {
        try {
            application.clearKochPanel();
            edges.clear();
            beginTime = System.currentTimeMillis();
        } catch (Exception e) {
        }
    }
    //</editor-fold>
    //</editor-fold>
}
