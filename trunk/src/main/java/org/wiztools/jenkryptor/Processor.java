/*
 * Processor.java
 *
 * Created on 07 January 2007, 22:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import static org.wiztools.jenkryptor.Globals.*;
import org.wiztools.wizcrypt.Callback;
import org.wiztools.wizcrypt.CipherKey;
import org.wiztools.wizcrypt.CipherKeyGen;
import org.wiztools.wizcrypt.WizCrypt;

/**
 *
 * @author subhash
 */
public class Processor {
    
    private static Processor processor = new Processor();
    
    private static final Executor exec = Executors.newFixedThreadPool(Globals.THREAD_SIZE);
    
    /** Creates a new instance of Processor */
    private Processor() {
    }
    
    public static Processor getInstance(){
        return processor;
    }
    
    public void process(){
        msgDisplayer.setStatus("Processing. . .");
        for(int i = 0; i < files.length; i++){
            final File file = files[i];
            
            Runnable r = new Runnable(){
                public void run(){
                    boolean semaphoreAcquired = false;
                    boolean gotLPE = false;
                    LabelProgressbarEnsc lpe = null;
                    
                    try{
                        SEMAPHORE.acquire();
                        semaphoreAcquired = true;
                        
                        lpe = PBPM.getLPE();
                        gotLPE = true;
                        
                        Callback cb = new WizCryptCallback(file, lpe);
                        
                        long fileSize = file.length();
                        InputStream is = new FileInputStream(file);
                        
                        String filePath = file.getCanonicalPath();
                        
                        if(mode == MODE_ENCRYPT){
                            OutputStream os = new FileOutputStream(filePath + ".wiz");
                            
                            CipherKey ck = CipherKeyGen.getCipherKeyForEncrypt(password);
                            
                            WizCrypt.encrypt(is, os, ck, cb, fileSize);
                        }
                        else{
                            String outPath = filePath.replaceFirst(".wiz$", "");
                            OutputStream os = new FileOutputStream(outPath);
                            
                            CipherKey ck = CipherKeyGen.getCipherKeyForDecrypt(password);
                            
                            WizCrypt.decrypt(is, os, ck, cb, fileSize);
                        }
                        
                    }
                    catch(Exception e){
                        Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
                        e.printStackTrace();
                    }
                    finally{
                        if(gotLPE){
                            PBPM.returnLPE(lpe);
                        }
                        if(semaphoreAcquired){
                            SEMAPHORE.release();
                        }
                    }
                }
            };
            exec.execute(r);
            msgDisplayer.setStatus(TITLE);
        }
    }
    
}
