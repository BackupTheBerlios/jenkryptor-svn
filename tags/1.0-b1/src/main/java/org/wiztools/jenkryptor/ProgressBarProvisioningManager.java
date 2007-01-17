/*
 * ProgressBarProvisioningManager.java
 *
 * Created on 07 January 2007, 20:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.util.Stack;

/**
 *
 * @author subhash
 */
public class ProgressBarProvisioningManager {
    
    private int number;
    private LabelProgressbarEnsc[] lpe_arr;
    
    private Stack<LabelProgressbarEnsc> stack = new Stack<LabelProgressbarEnsc>();
    
    /**
     * Creates a new instance of ProgressBarProvisioningManager
     */
    public ProgressBarProvisioningManager(final int number) {
        this.number = number;
        
        lpe_arr = new LabelProgressbarEnsc[number];
        
        for(int i = number-1; i >= 0; i--){
            lpe_arr[i] = new LabelProgressbarEnsc("Thread: "+(i+1));
            stack.push(lpe_arr[i]);
        }
        
    }
    
    public synchronized LabelProgressbarEnsc getLPE(){
        return stack.pop();
    }
    
    public synchronized void returnLPE(final LabelProgressbarEnsc lpb){
        stack.push(lpb);
    }
    
    public LabelProgressbarEnsc[] getLPEArr(){
        return lpe_arr;
    }
    
}
