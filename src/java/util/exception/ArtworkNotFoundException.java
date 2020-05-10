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
public class ArtworkNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ArtworkNotFoundException</code> without
     * detail message.
     */
    public ArtworkNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ArtworkNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ArtworkNotFoundException(String msg) {
        super(msg);
    }
}
