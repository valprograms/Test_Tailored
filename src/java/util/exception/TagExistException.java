/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author yiningxing
 */
public class TagExistException extends Exception {

    /**
     * Creates a new instance of <code>TagExistException</code> without detail
     * message.
     */
    public TagExistException() {
    }

    /**
     * Constructs an instance of <code>TagExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TagExistException(String msg) {
        super(msg);
    }
}
