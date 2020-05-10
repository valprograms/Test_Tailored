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
public class RatingExistException extends Exception {

    /**
     * Creates a new instance of <code>RatingExistException</code> without
     * detail message.
     */
    public RatingExistException() {
    }

    /**
     * Constructs an instance of <code>RatingExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RatingExistException(String msg) {
        super(msg);
    }
}
