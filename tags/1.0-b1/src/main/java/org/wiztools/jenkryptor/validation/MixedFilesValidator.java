/*
 * MixedFilesValidator.java
 *
 * Created on 10 January 2007, 22:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.validation;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.wiztools.jenkryptor.Globals;
import org.wiztools.jenkryptor.util.FileUtil;

/**
 *
 * @author subhash
 */
public class MixedFilesValidator extends Validator {
    
    /** Creates a new instance of MixedFilesValidator */
    public MixedFilesValidator() {
    }

    @Override
    public void validate(File[] files) throws ValidatorException {
        try{
            FileUtil.getFileNamesCSV(files);
        }
        catch(IOException ioe){
            throw new ValidatorException(ioe.getMessage());
        }
        
        if(next != null){
            next.validate(files);
        }
    }
    
}
