/*
 * Globals.java
 *
 * Created on 07 January 2007, 22:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.io.File;

/**
 *
 * @author subhash
 */
public class Globals {
    
    public static final int THREAD_SIZE = 6;
    
    public static final ProgressBarProvisioningManager PBPM = new 
            ProgressBarProvisioningManager(Globals.THREAD_SIZE);
    
    public static final int MODE_ENCRYPT = 0;
    public static final int MODE_DECRYPT = 1;
    
    public static final String TITLE = "jEnkryptor";
    
    public static File[] files;
    
    public static int mode;
    
    public static String password;
    
    public static MessageDisplay msgDisplayer;
    
    public static MainJFrame MAIN_FRAME;
    
    public static boolean isRunning = false;
    
    /** Creates a new instance of Globals */
    private Globals() {
    }
    
}
