/*
 * StringUtil.java
 *
 * Created on January 10, 2007, 5:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.util;

import java.io.File;

/**
 *
 * @author schandran
 */
public class StringUtil {
    
    /** Creates a new instance of StringUtil */
    private StringUtil() {
    }
    
    private static final int PATH_SIZE = 40;
    
    public static String getCompactedPath(String path){
        if(path.length() > PATH_SIZE){
            // 5 is for Windows c:\ (3rd location) or for UNIX /home (1st location)
            int idx_start = path.indexOf(File.separatorChar, 5);
            
            if(idx_start == -1){
                return path;
            }
            idx_start += 1;
            
            int idx_end = path.lastIndexOf(File.separatorChar);
            
            if(idx_start >= idx_end){
                return path;
            }
            
            String str1 = path.substring(0, idx_start);
            String str2 = path.substring(idx_end);
            
            return str1 + "[...]" + str2;
        }
        return path;
    }
    
}
