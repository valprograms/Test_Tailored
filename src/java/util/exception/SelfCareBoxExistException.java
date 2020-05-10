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
public class SelfCareBoxExistException extends Exception {

    /**
     * Creates a new instance of <code>SelfCareBoxExistException</code> without
     * detail message.
     */
    public SelfCareBoxExistException() {
    }

    /**
     * Constructs an instance of <code>SelfCareBoxExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SelfCareBoxExistException(String msg) {
        super(msg);
    }
}
