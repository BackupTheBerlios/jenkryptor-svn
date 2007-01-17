/*
 * WizCryptCallback.java
 *
 * Created on 07 January 2007, 23:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor;

import java.io.File;
import java.io.IOException;
import org.wiztools.jenkryptor.util.StringUtil;
import org.wiztools.wizcrypt.Callback;

/**
 *
 * @author subhash
 */
public class WizCryptCallback implements Callback {
    
    private final File file;
    private final LabelProgressbarEnsc lpe;
    private String originalLabel;
    
    /** Creates a new instance of WizCryptCallback */
    public WizCryptCallback(final File file, final LabelProgressbarEnsc lpe) {
        this.file = file;
        this.lpe = lpe;
    }

    public void begin() {
        originalLabel = lpe.getLabelText();
        String path = StringUtil.getCompactedPath(file.getAbsolutePath());
        lpe.setLabel(path);
    }

    public void notifyProgress(long value) {
        lpe.setProgress((int)value);
    }

    public void end() {
        lpe.setLabel(originalLabel);
        lpe.setProgress(0);
    }
    
}
