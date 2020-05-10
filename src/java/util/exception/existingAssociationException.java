/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author decimatum
 */
public class existingAssociationException extends Exception {

    /**
     * Creates a new instance of <code>existingAssociationException</code>
     * without detail message.
     */
    public existingAssociationException() {
    }

    /**
     * Constructs an instance of <code>existingAssociationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public existingAssociationException(String msg) {
        super(msg);
    }
}
