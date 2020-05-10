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
public class TransactionExistException extends Exception {

    /**
     * Creates a new instance of <code>TransactionExistException</code> without
     * detail message.
     */
    public TransactionExistException() {
    }

    /**
     * Constructs an instance of <code>TransactionExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TransactionExistException(String msg) {
        super(msg);
    }
}
