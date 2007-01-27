/*
 * ValidatorException.java
 *
 * Created on 10 January 2007, 22:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.validation;

/**
 *
 * @author subhash
 */
public class ValidatorException extends Exception {
    
    /** Creates a new instance of ValidatorException */
    public ValidatorException() {
    }
    
    public ValidatorException(final String msg) {
        super(msg);
    }
    
}
