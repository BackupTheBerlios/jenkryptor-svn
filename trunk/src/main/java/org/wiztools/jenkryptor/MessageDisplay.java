/*
 * MessageDisplay.java
 *
 * Created on 08 January 2007, 00:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author subhash
 */
public class MessageDisplay {
    
    private final JLabel jlStatus;
    private final JTextArea jta;
    
    /** Creates a new instance of MessageDisplay */
    public MessageDisplay(final JLabel jl, final JTextArea jta) {
        this.jlStatus = jl;
        this.jta = jta;
    }
    
    public void setStatus(final String str){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jlStatus.setText(str);
            }
        });
    }
    
    public void appendMessage(final String str){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jta.append("\n" + str);
            }
        });
    }
}
