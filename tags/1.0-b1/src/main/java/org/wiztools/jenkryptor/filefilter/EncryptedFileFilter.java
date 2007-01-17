/*
 * EncryptedFileFilter.java
 *
 * Created on January 12, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.filefilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author schandran
 */
public class EncryptedFileFilter extends FileFilter {
    
    /** Creates a new instance of EncryptedFileFilter */
    public EncryptedFileFilter() {
    }

    public boolean accept(File f) {
        return f.getAbsolutePath().endsWith(".wiz")?true:false;
    }

    public String getDescription() {
        return "Encrypted files";
    }
    
}
