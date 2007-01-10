/*
 * LabelProgressbarEnsc.java
 *
 * Created on 07 January 2007, 20:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author subhash
 */
public class LabelProgressbarEnsc {
    
    private JLabel jl;
    private JProgressBar jpb;
    
    /** Creates a new instance of LabelProgressbarEnsc */
    public LabelProgressbarEnsc(String label) {
        jl = new JLabel(label);
        jpb = new JProgressBar();
    }
    
    public void setLabel(final String label){
        try{
            SwingUtilities.invokeAndWait(new Runnable(){
                public void run(){
                    jl.setText(label);
                }
            });
        }
        catch(InvocationTargetException ite){
            ite.printStackTrace();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    public String getLabelText(){
        return jl.getText();
    }
    
    public JLabel getLabel(){
        return jl;
    }
    
    public void setProgress(final int i){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jpb.setValue(i);
            }
        });
    }
    
    public JProgressBar getProgressBar(){
        return jpb;
    }
    
}
