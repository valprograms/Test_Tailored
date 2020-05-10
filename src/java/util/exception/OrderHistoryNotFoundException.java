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
public class OrderHistoryNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>OrderHistoryNotFoundException</code>
     * without detail message.
     */
    public OrderHistoryNotFoundException() {
    }

    /**
     * Constructs an instance of <code>OrderHistoryNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderHistoryNotFoundException(String msg) {
        super(msg);
    }
}
