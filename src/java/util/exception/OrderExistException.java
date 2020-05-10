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
public class OrderExistException extends Exception {

    /**
     * Creates a new instance of <code>OrderExistException</code> without detail
     * message.
     */
    public OrderExistException() {
    }

    /**
     * Constructs an instance of <code>OrderExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderExistException(String msg) {
        super(msg);
    }
}
