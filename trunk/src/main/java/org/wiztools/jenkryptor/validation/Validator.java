/*
 * Validator.java
 *
 * Created on 10 January 2007, 22:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.jenkryptor.validation;

import java.io.File;

/**
 *
 * @author subhash
 */
public abstract class Validator {
    protected Validator next;
    
    public Validator setNext(Validator validator){
        this.next = validator;
        return this;
    }
    
    public abstract void validate(File[] files) throws ValidatorException;
}
