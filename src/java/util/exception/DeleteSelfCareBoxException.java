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
public class DeleteSelfCareBoxException extends Exception {

    /**
     * Creates a new instance of <code>DeleteSelfCareBoxException</code> without
     * detail message.
     */
    public DeleteSelfCareBoxException() {
    }

    /**
     * Constructs an instance of <code>DeleteSelfCareBoxException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteSelfCareBoxException(String msg) {
        super(msg);
    }
}
