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
    
    private static Processor processor = new Processor();
    
    /** Creates a new instance of Processor */
    private Processor() {
    }
    
    public static Processor getInstance(){
        return processor;
    }
    
    public void process(){
        Runnable process = new Runnable(){
            public void run(){
                Globals.MAIN_FRAME.freeze();
                msgDisplayer.setStatus("Processing. . .");
        
                ExecutorService exec = Executors.newFixedThreadPool(Globals.THREAD_SIZE);
                for(int i = 0; i < files.length; i++){
                    final File file = files[i];

                    Runnable r = new ProcessThread(file);
                    exec.execute(r);
                    msgDisplayer.setStatus(TITLE);
                }
                exec.shutdown();
                // Just put an insane amount of time:
                try{
                    exec.awaitTermination(999999999, TimeUnit.DAYS);
                }
                catch(InterruptedException ie){
                    ie.printStackTrace();
                }
                Globals.MAIN_FRAME.unfreeze();
            }
        };
        
        new Thread(process).start();
    }
    
    class ProcessThread implements Runnable{
        
        private File file;
        
        ProcessThread(File file){
            this.file = file;
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

                    String filePath = file.getAbsolutePath();

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
                catch(PasswordMismatchException e){
                    Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
                }
                catch(IOException e){
                    Globals.msgDisplayer.appendMessage("ERROR: "+e.getMessage());
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
