/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author mac
 */
public class SelfCareBoxNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SelfCareBoxNotFoundException</code>
     * without detail message.
     */
    public SelfCareBoxNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SelfCareBoxNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public SelfCareBoxNotFoundException(String msg) {
        super(msg);
    }
}
