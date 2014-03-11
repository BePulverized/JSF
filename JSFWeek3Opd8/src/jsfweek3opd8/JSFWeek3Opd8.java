/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsfweek3opd8;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phinux
 */
public class JSFWeek3Opd8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("gnome-calculator");
            Process process = processBuilder.start();
            Thread.sleep(2000);
            process.destroy();
            Thread.sleep(500);
            process = Runtime.getRuntime().exec("gnome-calculator");
            Thread.sleep(2000);
            process.destroy();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(JSFWeek3Opd8.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
