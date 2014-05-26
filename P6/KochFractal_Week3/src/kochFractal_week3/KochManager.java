/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kochFractal_week3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */

public class KochManager
{
    Scanner scan;
    private KochFractalReadWrite application;
    Edge edge;
    ArrayList<Edge> edges = new ArrayList<>();
    private double beginTime;
    private double endTime = 0;

    public KochManager(KochFractalReadWrite application)
    {        
        this.application = application;
        
       // drawEdges();
        drawBufferedEdges();
    }    

    public void drawEdges() throws FileNotFoundException, IOException
    { 
       application.clearKochPanel();
       edges.clear();
       beginTime = System.currentTimeMillis();
       
       
       RandomAccessFile memoryMappedFile = new RandomAccessFile("EdgeFile.txt", "rw");
       int level = memoryMappedFile.readInt();
       int nrEdges = memoryMappedFile.readInt();
       
       
       for(int i=0; i<nrEdges; i++)
       {
            double X1 = memoryMappedFile.readDouble();
            double Y1 = memoryMappedFile.readDouble();
            double X2 = memoryMappedFile.readDouble();
            double Y2 = memoryMappedFile.readDouble();
            edge=new Edge(X1, Y1, X2, Y2);
            edges.add(edge);   
       }
       
       //<editor-fold defaultstate="collapsed" desc="oude reader">
       //Lees uit text file
//       Edge returnvalue = null;
//        try {
//            FileReader fr = new FileReader("Edges.txt");
//            Scanner input = new Scanner(fr);
//            
//            while(input.hasNextLine())
//            {
//                String regel = input.nextLine();
//
//                String[] parameter = regel.split(",");
//
//                double X1 = Double.parseDouble(parameter[0]);
//                double Y1 = Double.parseDouble(parameter[1]);
//                double X2 = Double.parseDouble(parameter[2]);
//                double Y2 = Double.parseDouble(parameter[3]);
//                
//                edges.add(new Edge(X1,Y1,X2,Y2));
//            }
//            
//            input.close();
//            endTime = (System.currentTimeMillis() - beginTime)/1000;
//            application.setTextCalc(endTime + " sec");
//            for(Edge edge : edges)
//            {
//                application.drawEdge(edge);
//            }
//            
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        //Lees uit bin file
        /*try {
            
            in = new ObjectInputStream(new FileInputStream("Edges.bin"));
            
            while(true)
            {
            edges.add((Edge) in.readObject());
            } 
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } */      
        //</editor-fold>

       System.out.println("Reading from Memory mapped file is completed.");
        
       endTime = (System.currentTimeMillis() - beginTime)/1000;
       application.setTextCalc(endTime + " sec");
        
        for (Edge e : edges)
            {
             application.drawEdge(e);                
            }
    }
    
    public void drawBufferedEdges()
    {
        try
        {
            application.clearKochPanel();
            edges.clear();
            beginTime = System.currentTimeMillis();
        }
        catch(Exception e)
        {
        }
            //Lees uit text file met buffer
//        try
//        {
//            BufferedReader reader = new BufferedReader(new FileReader(new File("Edges.txt")));
//            try
//            {
//                while(reader.readLine() != null)
//                {
//                    String regel = reader.readLine();
//                    
//                    String[] parameter = regel.split(",");
//                    
//                    double X1 = Double.parseDouble(parameter[0]);
//                    double Y1 = Double.parseDouble(parameter[1]);
//                    double X2 = Double.parseDouble(parameter[2]);
//                    double Y2 = Double.parseDouble(parameter[3]);
//                    
//                    edges.add(new Edge(X1,Y1,X2,Y2));
//                }
//                reader.close();
//                endTime = (System.currentTimeMillis() - beginTime)/1000;
//                application.setTextCalc(endTime + " sec");
//            } catch (IOException ex)
//            {
//                Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            for(Edge edge : edges)
//            {
//                application.drawEdge(edge);
//            }
//        } catch (FileNotFoundException ex)
//        {
//            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
            
            //Lees uit bin file met buffer

            
//       in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Edges.bin")));
//                
//       while(true)
//        {
//            edges.add((Edge)in.readObject());
//        }
//                
//        }
//        catch(ClassNotFoundException | IOException ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//        try
//        {
//            in.close();
//            endTime = (System.currentTimeMillis() - beginTime)/1000;
//            application.setTextCalc(endTime + " sec");
//            for(Edge edge : edges)
//            {
//                application.drawEdge(edge);
//            }
//        }
//        catch(IOException ex)
//        {
//            System.out.println(ex.getMessage());
//        }
    }

}


