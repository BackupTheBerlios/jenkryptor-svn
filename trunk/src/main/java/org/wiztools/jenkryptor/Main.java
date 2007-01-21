/*
 * Main.java
 *
 * Created on January 10, 2007, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 *
 * @author schandran
 */
public class Main {
    
    /** Creates a new instance of Main */
    public static void main(String[] str) {
        
        try{
            LogManager.getLogManager().readConfiguration(
                Main.class.getClassLoader()
                .getResourceAsStream("org/wiztools/jenkryptor/logging.properties"));
        }
        catch(IOException ioe){
            assert true: "Logger configuration load failed!";
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    
}
