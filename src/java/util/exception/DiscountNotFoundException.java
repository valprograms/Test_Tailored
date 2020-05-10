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
public class DiscountNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>DiscountNotFoundException</code> without
     * detail message.
     */
    public DiscountNotFoundException() {
    }

    /**
     * Constructs an instance of <code>DiscountNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DiscountNotFoundException(String msg) {
        super(msg);
    }
}
