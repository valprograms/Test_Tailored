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
public class ArtworkPriceNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ArtworkPriceNotFoundException</code>
     * without detail message.
     */
    public ArtworkPriceNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ArtworkPriceNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ArtworkPriceNotFoundException(String msg) {
        super(msg);
    }
}
