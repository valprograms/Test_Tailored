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
public class ArtworkNotCreatedException extends Exception {

    /**
     * Creates a new instance of <code>ArtworkNotCreatedException</code> without
     * detail message.
     */
    public ArtworkNotCreatedException() {
    }

    /**
     * Constructs an instance of <code>ArtworkNotCreatedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ArtworkNotCreatedException(String msg) {
        super(msg);
    }
}
