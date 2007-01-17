/*
 * FileUtil.java
 *
 * Created on 10 January 2007, 22:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.util;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.wiztools.jenkryptor.Globals;
import org.wiztools.jenkryptor.validation.ValidatorException;

/**
 *
 * @author subhash
 */
public class FileUtil {
    
    /** Creates a new instance of FileUtil */
    private FileUtil() {
    }
    
    public static String getFileNamesCSV(File[] files) throws ValidatorException, IOException{
        StringBuffer sb = new StringBuffer();
        
        int filesEndingWithWiz = 0;
        for(int i=0; i<files.length; i++){
            String path = files[i].getCanonicalPath();
            if(path.endsWith(".wiz")){
                filesEndingWithWiz += 1;
            }
            path = path.replaceAll("\"", "\\\"");
            sb.append('"').append(path).append('"').append(',');
        }
        sb.deleteCharAt(sb.length()-1);

        Globals.mode = Globals.MODE_ENCRYPT;
        if(files.length == filesEndingWithWiz){
            Globals.mode = Globals.MODE_DECRYPT;
            Globals.msgDisplayer.setStatus("Decrypt mode set");
        }
        else if(filesEndingWithWiz != 0 && files.length > filesEndingWithWiz){
            throw new ValidatorException("Please select one type of file!");
        }
        else{
            Globals.msgDisplayer.setStatus("Encrypt mode set");
        }

        Globals.files = files;
        
        return sb.toString();
    }
    
}
