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
public class SelfCareSubscriptionDiscountExistException extends Exception {

    /**
     * Creates a new instance of
     * <code>SelfCareSubscriptionDiscountExistException</code> without detail
     * message.
     */
    public SelfCareSubscriptionDiscountExistException() {
    }

    /**
     * Constructs an instance of
     * <code>SelfCareSubscriptionDiscountExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SelfCareSubscriptionDiscountExistException(String msg) {
        super(msg);
    }
}
