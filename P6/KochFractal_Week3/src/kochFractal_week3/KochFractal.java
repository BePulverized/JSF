/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kochFractal_week3;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Boots
 */
public class KochFractal{

    private int level = 1;      // The current level of the fractal
    private int nrOfEdges = 3;  // The number of edges in the current level of the fractal
    private float hue;          // Hue value of color for next edge
    private boolean cancelled;  // Flag to indicate that calculation has been cancelled 
    private double beginTime = 0;
    private double endTime = 0;
    int count = 0;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    
    
   
    

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
    
    public ArrayList getEdges()
    {
        return edges;
    }

    public void generateLeftEdge() {
        hue = 0f;
        cancelled = false;
        drawKochEdge(0.5, 0.0, (1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
    }

    public void generateBottomEdge() {
        hue = 1f / 3f;
        cancelled = false;
        drawKochEdge((1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, (1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, level);
    }

    public void generateRightEdge() {
        hue = 2f / 3f;
        cancelled = false;
        drawKochEdge((1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, 0.5, 0.0, level);
        
    }
    
    public void cancel() {
        cancelled = true;
    }

    public void setLevel(int lvl) {
        level = lvl;
        nrOfEdges = (int) (3 * Math.pow(4, level - 1));
    }

    public int getLevel() {
        return level;
    }

    public int getNrOfEdges() {
        return nrOfEdges;
    }
    
    public double getEndTime()
    {
        return (endTime - beginTime)/1000;
    }
    
    
    public void writeEdge(ArrayList<Edge> edges) throws FileNotFoundException, IOException {
        //mapping writer
        RandomAccessFile memoryMappedFile = new RandomAccessFile("EdgeFile.txt", "rw");
        int size = edges.toString().getBytes().length;
        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);
        out.position(0);
        out.putInt(level);
        out.putInt(nrOfEdges);
        for(Edge edge : edges)
        {
            out.putDouble(edge.X1);
            out.putDouble(edge.Y1);
            out.putDouble(edge.X2);
            out.putDouble(edge.Y2);
        }
        System.out.println("Writing to memory mapped file is completed");
        
        //<editor-fold defaultstate="collapsed" desc="oude writer">
            //oude writer
        /*FileWriter fw;
        FileWriter fwB;
        beginTime = System.currentTimeMillis();
        
        try {       
            //Naar binaire file zonder buffer            
      
//           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Edges.bin"));
//           for(Edge edge : edges)
//           {
//               out.writeObject(edge);
//           }
//           out.close();          
            
            //Naar binaire file met buffer
           
           ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Edges.bin")));
           for(Edge edge : edges)
           {
               out.writeObject(edge);
           }
           out.close();
            
//            Naar textfile zonder buffer
            
//            fw = new FileWriter("Edges.txt");
//            PrintWriter pr = new PrintWriter(fw);
//            for(Edge edge : edges)
//            {
//                pr.println(edge.toString());
//            }
//            pr.close();
            
            //Naar textfile met buffer
         
//            fw = new FileWriter("Edges.txt");
//            BufferedWriter pr = new BufferedWriter(fw);
//            for(Edge edge : edges)
//            {
//                pr.write(edge.toString());                
//            }
//            pr.close();
            
            endTime = System.currentTimeMillis();
        }
        catch(IOException ex)
        {
            
        }*/
        //</editor-fold>
         
    }
    
}

