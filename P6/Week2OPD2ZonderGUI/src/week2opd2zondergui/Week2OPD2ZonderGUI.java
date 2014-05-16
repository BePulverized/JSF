package week2opd2zondergui;

import callculate.Edge;
import callculate.KochFractal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Console aplicatie die de berekende Edges in een file zet. Deze console
 * applicatie vraagt aan de gebruiker voor welk level de edges gegenereerd
 * moeten worden, en zet die edges vervolgens in een file (dus niet in een
 * array, en ook niet tekenen). Het level moet ook in de file staan. De
 * betreffende file moet zich op de hierboven gemounte nieuwe disk bevinden.
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/19
 */
public class Week2OPD2ZonderGUI implements Observer{

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final Scanner input;
    private final KochFractal koch;
    private final String filename = "/home/phinux/Workspaces/Portable/Edges";
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor/Main">
    public Week2OPD2ZonderGUI() {
        input = new Scanner(System.in);
        this.koch = new KochFractal(); 
        koch.addObserver(this);     
    }
    
    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Week2OPD2ZonderGUI console = new Week2OPD2ZonderGUI();
        console.start();        
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Operations">
    
    private void start() {
        int choice = kiesLevel();
        koch.setLevel(choice);
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
    }
    
    int kiesLevel() {
        System.out.println();
        int maxNr = 10;
        int nr;
        nr = readInt("maak een keuze uit 0 t/m " + maxNr);
        while (nr < 0 || nr > maxNr) {
            nr = readInt("maak een keuze uit 0 t/m " + maxNr);
        }
        input.nextLine();
        return nr;
    }
    
    int readInt(String helptekst) {
        boolean invoerOk = false;
        int invoer = -1;
        while (!invoerOk) {
            try {
                System.out.print(helptekst + " ");
                invoer = input.nextInt();
                invoerOk = true;
            } catch (InputMismatchException exc) {
                System.out.println("Let op, invoer moet een getal zijn!");
                input.nextLine();
            }

        }
        return invoer;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            Edge e = (Edge) arg;
            FileOutputStream fos;
            ObjectOutputStream out;
            
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            
            out.writeObject(e);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Week2OPD2ZonderGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Week2OPD2ZonderGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

}
