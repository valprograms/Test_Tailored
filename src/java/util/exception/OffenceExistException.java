/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Kaijing
 */
public class OffenceExistException extends Exception {

    /**
     * Creates a new instance of <code>OffenceExistException</code> without
     * detail message.
     */
    public OffenceExistException() {
    }

    /**
     * Constructs an instance of <code>OffenceExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OffenceExistException(String msg) {
        super(msg);
    }
}
