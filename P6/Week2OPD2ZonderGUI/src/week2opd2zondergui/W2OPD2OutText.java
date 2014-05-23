package week2opd2zondergui;

import callculate.Edge;
import callculate.KochFractal;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
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
public class W2OPD2OutText implements Observer{

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final Scanner input;
    private final KochFractal koch;
    private File file;
    private FileWriter fw;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor/Main">
    public W2OPD2OutText() {
        input = new Scanner(System.in);
        this.koch = new KochFractal(); 
        koch.addObserver(this);     
    }
    
    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        W2OPD2OutText console = new W2OPD2OutText();
        console.start();        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Operations">
    private void start() {
        try {
            file = controle("/home/phinux/Workspaces/Portable/Edges");
        } catch (IOException ex) {
            Logger.getLogger(W2OPD2OutText.class.getName()).log(Level.SEVERE, null, ex);
        }
        int choice = kiesLevel();
        koch.setLevel(choice);
        openFileStream();
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(W2OPD2OutText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    File controle(String bestandslocatie) throws IOException {
        File f = new File(bestandslocatie);
        if(!f.exists() && f.isDirectory()) {
          f = new File(bestandslocatie + "/Edges"); 
          f.createNewFile(); 
        } 
        else if(!f.exists() && !f.isDirectory()){
            f.createNewFile();
        }
        return f;         
    }
    
    int kiesLevel() {
        System.out.println();
        int maxNr = 10;
        int nr;
        nr = readInt("maak een keuze uit 1 t/m " + maxNr);
        while (nr < 1 || nr > maxNr) {
            nr = readInt("maak een keuze uit 1 t/m " + maxNr);
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
    
    private void openFileStream(){
        try {
            fw  = new FileWriter(file);
        } catch (IOException ex) {
            Logger.getLogger(W2OPD2OutText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
            Edge e = (Edge) arg;
                     
        try {
            fw.write(e.toString());
        } catch (IOException ex) {
            Logger.getLogger(W2OPD2OutText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
}
