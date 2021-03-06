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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import static org.wiztools.jenkryptor.Globals.*;
import org.wiztools.wizcrypt.Callback;
import org.wiztools.wizcrypt.CipherKey;
import org.wiztools.wizcrypt.CipherKeyGen;
import org.wiztools.wizcrypt.PasswordMismatchException;
import org.wiztools.wizcrypt.WizCrypt;

/**
 *
 * @author subhash
 */
public class Processor {
    
    private static final Processor processor = new Processor();
    private static final Logger LOG = Logger.getLogger(Processor.class.getName());
    
    /** Creates a new instance of Processor */
    private Processor() {
    }
    
    public static Processor getInstance(){
        return processor;
    }
    
    public void process(){
        Runnable process = new Runnable(){
            public void run(){
                Globals.isRunning = true;
                Globals.MAIN_FRAME.freeze();
                msgDisplayer.setStatus("Processing. . .");
        
                ExecutorService exec = Executors.newFixedThreadPool(Globals.THREAD_SIZE);
                for(int i = 0; i < files.length; i++){
                    final File file = files[i];

                    Runnable r = new ProcessThread(file);
                    exec.execute(r);
                }
                LOG.finest("Awaiting shutdown of threadpool. . .");
                exec.shutdown();
                LOG.finest("Shutdown complete, awaiting termination. . .");
                
                // Just put an insane amount of time:
                try{
                    exec.awaitTermination(999999999, TimeUnit.DAYS);
                    LOG.finest("Termination successful!");
                }
                catch(InterruptedException ie){
                    ie.printStackTrace();
                }
                Globals.MAIN_FRAME.unfreeze();
                Globals.isRunning = false;
                msgDisplayer.setStatus(TITLE);
            }
        };
        
        new Thread(process).start();
    }
    
    static class ProcessThread implements Runnable{
        
        private static final Logger LOG = Logger.getLogger(ProcessThread.class.getName());
        
        private File file;
        private String filePath;
        
        ProcessThread(File file){
            this.file = file;
            this.filePath = file.getAbsolutePath();
        }
        
        public void run(){
            boolean gotLPE = false;
            LabelProgressbarEnsc lpe = null;

            try{

                lpe = PBPM.getLPE();
                gotLPE = true;

                Callback cb = new WizCryptCallback(file, lpe);

                long fileSize = file.length();

                try{
                    InputStream is = new FileInputStream(file);

                    if(mode == MODE_ENCRYPT){
                        File outFile = new File(filePath + ".wiz");
                        if(!outFile.exists() || 
                                (outFile.exists() && Preferences.overwriteDestination_pref)){
                            OutputStream os = new FileOutputStream(outFile);

                            CipherKey ck = CipherKeyGen.getCipherKeyForEncrypt(password);

                            WizCrypt.encrypt(is, os, ck, cb, fileSize);
                            
                            Globals.msgDisplayer.appendMessage("Done: " + outFile.getAbsolutePath());
                            
                            if(Preferences.deleteSource_pref){
                                file.delete();
                            }
                        }
                        else{
                            Globals.msgDisplayer.appendMessage("SKIPPING: "
                                    + outFile.getAbsolutePath() + " exists!");
                        }
                    }
                    else{
                        LOG.finest("Decrypting...");
                        String outPath = filePath.replaceFirst(".wiz$", "");
                        LOG.finest("outPath: "+outPath);
                        File outFile = new File(outPath);
                        
                        LOG.finest("!outFile.exists()"+!outFile.exists());
                        
                        if(!outFile.exists() ||
                                (outFile.exists() && Preferences.overwriteDestination_pref)){
                            CipherKey ck = CipherKeyGen.getCipherKeyForDecrypt(password);
                            
                            OutputStream os = new FileOutputStream(outFile);

                            LOG.finest("Doing decrypt operation...");
                            WizCrypt.decrypt(is, os, ck, cb, fileSize);

                            Globals.msgDisplayer.appendMessage("Done: " + outPath);

                            if(Preferences.deleteSource_pref){
                                LOG.finest("Deleting source...");
                                file.delete();
                            }
                        }
                        else{
                            Globals.msgDisplayer.appendMessage("SKIPPING: "
                                    + outFile.getAbsolutePath() + " exists!");
                        }
                    }
                }
                catch(PasswordMismatchException e){
                    Globals.msgDisplayer.appendMessage("ERROR: " +
                            e.getMessage() + filePath);
                }
                catch(IOException e){
                    Globals.msgDisplayer.appendMessage("ERROR: " +
                            e.getMessage() + filePath);
                }
            }
            catch(NoSuchAlgorithmException e){
                Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
            }
            catch(InvalidKeyException e){
                Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
            }
            catch(NoSuchPaddingException e){
                Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
            }
            finally{
                if(gotLPE){
                    PBPM.returnLPE(lpe);
                }
            }
        }
    }
    
}
