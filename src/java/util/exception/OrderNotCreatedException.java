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
public class OrderNotCreatedException extends Exception {

    /**
     * Creates a new instance of <code>OrderNotCreatedException</code> without
     * detail message.
     */
    public OrderNotCreatedException() {
    }

    /**
     * Constructs an instance of <code>OrderNotCreatedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderNotCreatedException(String msg) {
        super(msg);
    }
}
