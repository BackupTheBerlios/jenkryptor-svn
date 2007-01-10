/*
 * SameFileValidator.java
 *
 * Created on 10 January 2007, 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.validation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author subhash
 */
public class SameFileValidator extends Validator {
    
    /** Creates a new instance of SameFileValidator */
    public SameFileValidator() {
    }

    @Override
    public void validate(File[] files) throws ValidatorException {
        List<String> ll = new ArrayList<String>();
        try{
            for(File f: files){
                String absPath = f.getCanonicalPath();
                System.out.println("absPath: "+absPath);
                if(ll.contains(absPath)){
                    throw new ValidatorException("Duplicate files: "
                            +absPath);
                }
                ll.add(absPath);
            }
        }
        catch(IOException ioe){
            throw new ValidatorException(ioe.getMessage());
        }
        
        if(next != null){
            next.validate(files);
        }
    }
    
}
