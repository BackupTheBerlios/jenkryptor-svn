/*
 * Main.java
 *
 * Created on January 10, 2007, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;

/**
 *
 * @author schandran
 */
public class Main {
    
    /** Creates a new instance of Main */
    public static void main(String[] str) {
        MutablePicoContainer pico = new DefaultPicoContainer();
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    
}
