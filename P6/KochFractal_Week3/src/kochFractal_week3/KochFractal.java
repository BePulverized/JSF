//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochFractal.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/24
 */
public class KochFractal {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private int level = 1;      // The current level of the fractal
    private int nrOfEdges = 3;  // The number of edges in the current level of the fractal
    private float hue;          // Hue value of color for next edge
    private boolean cancelled;  // Flag to indicate that calculation has been cancelled 
    private double beginTime = 0;
    private double endTime = 0;
    int count = 0;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //<editor-fold defaultstate="collapsed" desc="getEdges()">
    public ArrayList getEdges() {
        return edges;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Level">
    public void setLevel(int lvl) {
        level = lvl;
        nrOfEdges = (int) (3 * Math.pow(4, level - 1));
    }

    public int getLevel() {
        return level;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getNrOfEdges()">
    public int getNrOfEdges() {
        return nrOfEdges;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEndTime()">
    public double getEndTime() {
        return (endTime - beginTime) / 1000;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="drawKochEdge(ax, ay, bx, by, n)">
    private void drawKochEdge(double ax, double ay, double bx, double by, int n) {
        Edge e;
        if (!cancelled) {
            if (n == 1) {
                hue = hue + 1.0f / nrOfEdges;
                e = new Edge(ax, ay, bx, by);
                edges.add(e);

                count++;
            } else {
                double angle = Math.PI / 3.0 + Math.atan2(by - ay, bx - ax);
                double distabdiv3 = Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay)) / 3;
                double cx = Math.cos(angle) * distabdiv3 + (bx - ax) / 3 + ax;
                double cy = Math.sin(angle) * distabdiv3 + (by - ay) / 3 + ay;
                final double midabx = (bx - ax) / 3 + ax;
                final double midaby = (by - ay) / 3 + ay;
                drawKochEdge(ax, ay, midabx, midaby, n - 1);
                drawKochEdge(midabx, midaby, cx, cy, n - 1);
                drawKochEdge(cx, cy, (midabx + bx) / 2, (midaby + by) / 2, n - 1);
                drawKochEdge((midabx + bx) / 2, (midaby + by) / 2, bx, by, n - 1);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateLeftEdge()">
    public void generateLeftEdge() {
        hue = 0f;
        cancelled = false;
        drawKochEdge(0.5, 0.0, (1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateBottomEdge()">
    public void generateBottomEdge() {
        hue = 1f / 3f;
        cancelled = false;
        drawKochEdge((1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, (1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateRightEdge()">
    public void generateRightEdge() {
        hue = 2f / 3f;
        cancelled = false;
        drawKochEdge((1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, 0.5, 0.0, level);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="writeEdges(edges)">
    public void writeEdges(ArrayList<Edge> edges) throws FileNotFoundException, IOException {
        //mapping writer
        RandomAccessFile memoryMappedFile = new RandomAccessFile("EdgeFile.txt", "rw");
        int size = edges.toString().getBytes().length;
        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);
        out.position(0);
        out.putInt(level);
        out.putInt(nrOfEdges);
        for (Edge edge : edges) {
            out.putDouble(edge.X1);
            out.putDouble(edge.Y1);
            out.putDouble(edge.X2);
            out.putDouble(edge.Y2);
        }
        System.out.println("Writing to memory mapped file is completed");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancel()">
    public void cancel() {
        cancelled = true;
    }
    //</editor-fold>
    //</editor-fold>
}
