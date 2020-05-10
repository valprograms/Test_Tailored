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
public class DeleteArtworkException extends Exception {

    /**
     * Creates a new instance of <code>DeleteArtworkException</code> without
     * detail message.
     */
    public DeleteArtworkException() {
    }

    /**
     * Constructs an instance of <code>DeleteArtworkException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteArtworkException(String msg) {
        super(msg);
    }
}
